package org.codetome.zircon.internal.graphics

import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.behavior.DrawSurface
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.graphics.BaseTileImage
import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.api.util.Maybe
import java.util.*
import java.util.concurrent.Executors

class ThreadedTileImage<T : Any, S : Any>(
        size: Size,
        tileset: Tileset<T, S>,
        styleSet: StyleSet = StyleSet.defaultStyle())
    : BaseTileImage<T, S>(
        tileset = tileset,
        contents = mutableMapOf(),
        styleSet = styleSet,
        size = size) {

    private val executor = Executors.newSingleThreadExecutor()

    override fun getTileAt(position: Position): Maybe<Tile<T>> {
        return executor.submit<Maybe<Tile<T>>> {
            super.getTileAt(position)
        }.get()
    }

    override fun setTileAt(position: Position, tile: Tile<T>) {
        executor.submit {
            super.setTileAt(position, tile)
        }
    }

    override fun createSnapshot(): Map<Position, Tile<T>> {
        return executor.submit<Map<Position, Tile<T>>> {
            super.createSnapshot()
        }.get()
    }

    override fun clear() {
        executor.submit {
            super.clear()
        }
    }

    override fun drawOnto(surface: DrawSurface<T>, offset: Position) {
        executor.submit {
            super.drawOnto(surface, offset)
        }
    }
}
