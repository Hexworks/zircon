package org.hexworks.zircon.internal.graphics

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.DrawSurface
import org.hexworks.zircon.api.graphics.base.BaseTileImage
import org.hexworks.zircon.api.resource.TilesetResource

class DefaultTileImage(
        override val size: Size,
        override val tileset: TilesetResource,
        private val tiles: Map<Position, Tile> = mapOf())
    : BaseTileImage() {

    override fun getTileAt(position: Position) = Maybe.of(tiles[position] ?: Tile.empty())

    override fun fetchFilledPositions(): List<Position> {
        return tiles.map { it.key }
    }

    override fun drawOnto(surface: DrawSurface, position: Position) {
        toTileMap().forEach { (pos, tile) ->
            surface.setTileAt(pos + position, tile)
        }
    }

}
