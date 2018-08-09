package org.hexworks.zircon.internal.graphics

import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.behavior.DrawSurface
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.BaseTileGraphic
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.util.Maybe
import java.util.concurrent.Executors

class ThreadedTileGraphic(
        size: Size,
        tileset: TilesetResource,
        styleSet: StyleSet = StyleSet.defaultStyle())
    : BaseTileGraphic(
        tileset = tileset,
        contents = mutableMapOf(),
        styleSet = styleSet,
        size = size) {

    private val executor = Executors.newSingleThreadExecutor()

    override fun getTileAt(position: Position): Maybe<Tile> {
        return executor.submit<Maybe<Tile>> {
            super.getTileAt(position)
        }.get()
    }

    override fun setTileAt(position: Position, tile: Tile) {
        executor.submit {
            super.setTileAt(position, tile)
        }
    }

    override fun snapshot(): Map<Position, Tile> {
        return executor.submit<Map<Position, Tile>> {
            super.snapshot()
        }.get()
    }

    override fun clear() {
        executor.submit {
            super.clear()
        }
    }

    override fun drawOnto(surface: DrawSurface, position: Position) {
        executor.submit {
            super.drawOnto(surface, position)
        }
    }
}
