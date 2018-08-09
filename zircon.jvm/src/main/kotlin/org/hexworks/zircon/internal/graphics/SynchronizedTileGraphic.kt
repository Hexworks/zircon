package org.hexworks.zircon.internal.graphics

import org.hexworks.zircon.api.behavior.DrawSurface
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.BaseTileGraphic
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.util.Maybe

class SynchronizedTileGraphic(
        size: Size,
        tileset: TilesetResource,
        styleSet: StyleSet = StyleSet.defaultStyle())
    : BaseTileGraphic(
        tileset = tileset,
        contents = mutableMapOf(),
        styleSet = styleSet,
        size = size) {

    @Synchronized
    override fun getTileAt(position: Position): Maybe<Tile> {
        return super.getTileAt(position)
    }

    @Synchronized
    override fun setTileAt(position: Position, tile: Tile) {
        super.setTileAt(position, tile)
    }

    @Synchronized
    override fun snapshot(): Map<Position, Tile> {
        return super.snapshot()
    }

    @Synchronized
    override fun clear() {
        super.clear()
    }

    @Synchronized
    override fun drawOnto(surface: DrawSurface, position: Position) {
        super.drawOnto(surface, position)
    }
}
