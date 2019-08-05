package org.hexworks.zircon.internal.graphics

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.DrawSurface
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.graphics.base.BaseTileGraphics
import org.hexworks.zircon.api.resource.TilesetResource

/**
 * This is a non thread-safe, but very fast [TileGraphics]
 * implementation.
 */
class FastTileGraphics(
        size: Size,
        tileset: TilesetResource,
        initialTiles: Map<Position, Tile>)
    : BaseTileGraphics(
        tileset = tileset,
        initialSize = size) {

    override val tiles = initialTiles.toMutableMap()

    override fun drawOnto(surface: DrawSurface, position: Position) {
        tiles.putAll(surface.tiles.mapKeys { it.key + position })
    }

    override fun clear() {
        tiles.clear()
    }

    override fun setTileAt(position: Position, tile: Tile) {
        if (size.containsPosition(position)) {
            tiles[position]?.let { previous ->
                if (previous != tile) {
                    tiles[position] = tile
                }
            } ?: run {
                tiles[position] = tile
            }
        }
    }

    override fun transformTileAt(position: Position, tileTransformer: (Tile) -> Tile) {
        getTileAt(position).map { tile ->
            tiles[position] = tileTransformer(tile)
        }
    }
}
