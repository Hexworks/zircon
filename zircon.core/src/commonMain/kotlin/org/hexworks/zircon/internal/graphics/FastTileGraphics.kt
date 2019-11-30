package org.hexworks.zircon.internal.graphics

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.graphics.base.BaseTileGraphics
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.data.TileGraphicsState

/**
 * This is a non thread-safe, but very fast [TileGraphics] implementation.
 */
class FastTileGraphics(
        initialSize: Size,
        initialTileset: TilesetResource,
        initialTiles: Map<Position, Tile>)
    : BaseTileGraphics(
        initialTileset = initialTileset,
        initialSize = initialSize) {

    override val tiles = initialTiles.toMutableMap()

    override val state: TileGraphicsState
        get() = TileGraphicsState.create(
                tiles = tiles.toMap(),
                tileset = tileset,
                size = size)

    override fun draw(tileMap: Map<Position, Tile>, drawPosition: Position, drawArea: Size) {
        tileMap.asSequence()
                .filter { drawArea.containsPosition(it.key) && size.containsPosition(it.key + drawPosition) }
                .map { (key, value) -> key + drawPosition to value }
                .forEach { (pos, tile) ->
                    if (tile.isEmpty) {
                        tiles.remove(pos)
                    } else {
                        tiles[pos] = tile
                    }
                }
    }

    // TODO: regression test removal
    override fun draw(tile: Tile, drawPosition: Position) {
        if (size.containsPosition(drawPosition)) {
            if (tile.isEmpty) {
                tiles.remove(drawPosition)
            } else {
                tiles[drawPosition] = tile
            }
        }
    }

    override fun fill(filler: Tile) {
        if (filler.isNotEmpty) {
            size.fetchPositions().minus(tiles.keys.filter { it != Tile.empty() }).forEach {
                tiles[it] = filler
            }
        }
    }

    override fun clear() {
        tiles.clear()
    }

    // TODO: test removal
    override fun transformTileAt(position: Position, tileTransformer: (Tile) -> Tile) {
        getTileAt(position).map { tile ->
            val newTile = tileTransformer(tile)
            if (newTile.isEmpty) {
                tiles.remove(position)
            } else {
                tiles[position] = newTile
            }
        }
    }
}
