package org.hexworks.zircon.internal.graphics

import kotlinx.collections.immutable.PersistentMap
import kotlinx.collections.immutable.persistentMapOf
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.graphics.base.BaseTileGraphics
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.data.DefaultTileGraphicsState
import org.hexworks.zircon.internal.data.TileGraphicsState
import kotlin.jvm.Synchronized

/**
 * This is a thread-safe [TileGraphics] All read / write operations
 * ([getTileAt], [state], etc) are consistent even if concurrent write
 * operations are being performed. Use this implementation if you want
 * to read / write from multiple threads.
 */
class ThreadSafeTileGraphics(
        initialSize: Size,
        initialTileset: TilesetResource,
        initialTiles: PersistentMap<Position, Tile> = persistentMapOf()
) : BaseTileGraphics(
        initialSize = initialSize,
        initialTileset = initialTileset
) {

    override var tiles: PersistentMap<Position, Tile> = initialTiles
        private set

    override val state: TileGraphicsState
        get() = DefaultTileGraphicsState(
                size = size,
                tileset = tileset,
                tiles = tiles)

    @Synchronized
    override fun draw(tile: Tile, drawPosition: Position) {
        if (size.containsPosition(drawPosition)) {
            tiles = if (tile.isEmpty) {
                tiles.remove(drawPosition)
            } else {
                tiles.put(drawPosition, tile)
            }
        }
    }

    @Synchronized
    override fun draw(tileMap: Map<Position, Tile>, drawPosition: Position, drawArea: Size) {
        var newTiles = tiles
        val tilesToAdd = mutableMapOf<Position, Tile>()
        tileMap.asSequence()
                .filter { drawArea.containsPosition(it.key) && size.containsPosition(it.key + drawPosition) }
                .map { (key, value) -> key + drawPosition to value }
                .forEach { (pos, tile) ->
                    if (tile.isEmpty) {
                        newTiles = newTiles.remove(pos)
                    } else {
                        tilesToAdd[pos] = tile
                    }
                }
        newTiles = newTiles.putAll(tilesToAdd)
        tiles = newTiles
    }

    @Synchronized
    override fun clear() {
        tiles = tiles.clear()
    }

    @Synchronized
    override fun fill(filler: Tile) {
        if (filler.isNotEmpty) {
            val (currentTiles, _, currentSize) = state
            tiles = currentTiles.putAll(currentSize.fetchPositions()
                    .minus(currentTiles.filterValues { it.isNotEmpty }.keys)
                    .map { it to filler }.toMap())
        }
    }

    @Synchronized
    override fun transform(transformer: (Position, Tile) -> Tile) {
        var newTiles = tiles
        val tilesToAdd = mutableMapOf<Position, Tile>()
        size.fetchPositions().forEach { pos ->
            val tile = transformer(pos, newTiles.getOrElse(pos) { Tile.empty() })
            if (tile.isEmpty) {
                newTiles = newTiles.remove(pos)
            } else {
                tilesToAdd[pos] = tile
            }
        }
        newTiles = newTiles.putAll(tilesToAdd)
        tiles = newTiles
    }


}
