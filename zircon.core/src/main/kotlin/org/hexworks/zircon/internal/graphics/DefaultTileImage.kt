package org.hexworks.zircon.internal.graphics

import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.behavior.DrawSurface
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.TileImage
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.behavior.impl.DefaultBoundable

class DefaultTileImage(
        size: Size,
        private var tileset: TilesetResource,
        private val tiles: Map<Position, Tile> = mapOf(),
        boundable: Boundable = DefaultBoundable(size))
    : TileImage, Boundable by boundable {

    override fun getTileAt(position: Position) = Maybe.ofNullable(tiles[position])

    override fun drawOnto(surface: DrawSurface, position: Position) {
        toTileMap().forEach { (pos, tile) ->
            surface.setTileAt(pos + position, tile)
        }
    }

    override fun tileset() = tileset

    override fun useTileset(tileset: TilesetResource) {
        this.tileset = tileset
    }

}
