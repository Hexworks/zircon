package org.hexworks.zircon.internal.graphics

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
        initialTiles: Map<Position, Tile> = mapOf())
    : BaseTileGraphics(initialSize = initialSize, initialTileset = initialTileset) {

    override var tiles = persistentMapOf<Position, Tile>()
            .putAll(initialTiles)
        private set

    override var tileset: TilesetResource = initialTileset
        @Synchronized
        set(value) {
            value.checkCompatibilityWith(field)
            field = value
            currentState = currentState.copy(tileset = value)
        }

    override val state: TileGraphicsState
        get() = currentState

    private var currentState = DefaultTileGraphicsState(
            size = initialSize,
            tileset = initialTileset,
            tiles = initialTiles)

    @Synchronized
    override fun draw(tile: Tile, drawPosition: Position) {
        if (size.containsPosition(drawPosition)) {
            tiles = if (tile.isEmpty) {
                tiles.remove(drawPosition)
            } else {
                tiles.put(drawPosition, tile)
            }
            currentState = currentState.copy(tiles = tiles)
        }
    }

    @Synchronized
    override fun draw(tileMap: Map<Position, Tile>, drawPosition: Position, drawArea: Size) {
        tileMap.asSequence()
                .filter { drawArea.containsPosition(it.key) && size.containsPosition(it.key + drawPosition) }
                .map { (key, value) -> key + drawPosition to value }
                .forEach { (pos, tile) ->
                    tiles = if (tile.isEmpty) {
                        tiles.remove(pos)
                    } else {
                        tiles.put(pos, tile)
                    }
                }
        currentState = currentState.copy(tiles = tiles)
    }

    @Synchronized
    override fun clear() {
        tiles = tiles.clear()
        currentState = currentState.copy(tiles = tiles)
    }

    @Synchronized
    override fun fill(filler: Tile) {
        if (filler.isNotEmpty) {
            val (currentTiles, _, currentSize) = currentState
            tiles = tiles.putAll(currentSize.fetchPositions()
                    .minus(currentTiles.filterValues { it.isNotEmpty }.keys)
                    .map { it to filler }.toMap())
            currentState = currentState.copy(tiles = tiles)
        }
    }

    @Synchronized
    override fun transformTileAt(position: Position, tileTransformer: (Tile) -> Tile) {
        getTileAt(position).map { oldTile ->
            updateTile(position, tileTransformer(oldTile))
            currentState = currentState.copy(tiles = tiles)
        }
    }

    @Synchronized
    override fun transform(transformer: (Position, Tile) -> Tile) {
        size.fetchPositions().forEach { pos ->
            updateTile(pos, transformer(pos, tiles.getOrElse(pos) { Tile.empty() }))
        }
        currentState = currentState.copy(tiles = tiles)
    }

    private fun updateTile(pos: Position, tile: Tile) {
        tiles = if (tile.isEmpty) {
            tiles.remove(pos)
        } else {
            tiles.put(pos, tile)
        }
    }
}
