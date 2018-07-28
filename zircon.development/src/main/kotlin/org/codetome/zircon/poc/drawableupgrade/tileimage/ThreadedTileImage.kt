package org.codetome.zircon.poc.drawableupgrade.tileimage

import org.codetome.zircon.poc.drawableupgrade.position.GridPosition
import org.codetome.zircon.poc.drawableupgrade.drawables.DrawSurface
import org.codetome.zircon.poc.drawableupgrade.drawables.Drawable
import org.codetome.zircon.poc.drawableupgrade.drawables.TileImage
import org.codetome.zircon.poc.drawableupgrade.tile.Tile
import org.codetome.zircon.poc.drawableupgrade.tileset.Tileset
import java.util.*
import java.util.concurrent.Executors

data class ThreadedTileImage<T: Any, S: Any>(val width: Int,
                                             val height: Int,
                                             val tileset: Tileset<T, S>) : TileImage<T, S> {

    private val contents = mutableMapOf<GridPosition, Tile<T>>()
    private val executor = Executors.newSingleThreadExecutor()

    override fun tileset() = tileset

    override fun getTileAt(position: GridPosition): Optional<Tile<T>> {
        return Optional.ofNullable(executor.submit<Tile<T>?> { contents[position] }.get())
    }

    override fun setTileAt(position: GridPosition, tile: Tile<T>) {
        executor.submit {
            if (position.x < width && position.y < height) {
                contents[position] = tile
            }
        }
    }

    override fun createSnapshot(): Map<GridPosition, Tile<T>> {
        return executor.submit<Map<GridPosition, Tile<T>>> {
            contents.map { Pair(it.key, it.value) }.toMap()
        }.get()
    }

    override fun draw(drawable: Drawable<T>, offset: GridPosition) {
        drawable.drawOnto(this, offset)
    }

    override fun drawOnto(surface: DrawSurface<T>, offset: GridPosition) {
        executor.submit {
            contents.entries.forEach { (pos, tile) ->
                tile.drawOnto(surface, pos + offset)
            }
        }
    }
}
