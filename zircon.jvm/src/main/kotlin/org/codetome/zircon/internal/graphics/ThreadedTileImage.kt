package org.codetome.zircon.internal.graphics

import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.behavior.DrawSurface
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.graphics.BaseTileImage
import org.codetome.zircon.api.resource.TilesetResource
import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.api.util.Maybe
import java.util.*
import java.util.concurrent.Executors

class ThreadedTileImage(
        size: Size,
        tileset: TilesetResource<out Tile>,
        styleSet: StyleSet = StyleSet.defaultStyle())
    : BaseTileImage(
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

    override fun createSnapshot(): Map<Position, Tile> {
        return executor.submit<Map<Position, Tile>> {
            super.createSnapshot()
        }.get()
    }

    override fun clear() {
        executor.submit {
            super.clear()
        }
    }

    override fun drawOnto(surface: DrawSurface, offset: Position) {
        executor.submit {
            super.drawOnto(surface, offset)
        }
    }
}
