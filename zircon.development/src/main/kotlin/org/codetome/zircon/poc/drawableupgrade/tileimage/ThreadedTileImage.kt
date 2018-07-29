package org.codetome.zircon.poc.drawableupgrade.tileimage

import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.poc.drawableupgrade.drawables.DrawSurface
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.poc.drawableupgrade.tile.Tile
import org.codetome.zircon.poc.drawableupgrade.tileset.Tileset
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

    override fun getTileAt(position: Position): Optional<Tile<T>> {
        return executor.submit<Optional<Tile<T>>> {
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
