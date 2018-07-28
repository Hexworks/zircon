package org.codetome.zircon.poc.drawableupgrade.tileimage

import org.codetome.zircon.poc.drawableupgrade.position.GridPosition
import org.codetome.zircon.poc.drawableupgrade.drawables.DrawSurface
import org.codetome.zircon.poc.drawableupgrade.drawables.Drawable
import org.codetome.zircon.poc.drawableupgrade.drawables.TileImage
import org.codetome.zircon.poc.drawableupgrade.tile.Tile
import org.codetome.zircon.poc.drawableupgrade.tileset.Tileset
import java.util.*

/**
 * this is a basic building block which can be re-used by complex image
 * classes like layers, boxes, components, and more
 * all classes which are implementing the DrawSurface or the Drawable operations can
 * use this class as a base class just like how the TileGrid uses it
 */

data class MapTileImage<T: Any, S: Any>(val width: Int,
                                        val height: Int,
                                        val tileset: Tileset<T, S>) : TileImage<T, S> {

    private val contents = mutableMapOf<GridPosition, Tile<T>>()

    override fun tileset() = tileset

    override fun getTileAt(position: GridPosition): Optional<Tile<T>> {
        return Optional.ofNullable(contents[position])
    }

    override fun setTileAt(position: GridPosition, tile: Tile<T>) {
        if (position.x < width && position.y < height) {
            contents[position] = tile
        }
    }

    override fun createSnapshot(): Map<GridPosition, Tile<T>> {
        return contents.map { Pair(it.key, it.value) }.toMap()
    }

    override fun draw(drawable: Drawable<T>, offset: GridPosition) {
        drawable.drawOnto(this, offset)
    }

    override fun drawOnto(surface: DrawSurface<T>, offset: GridPosition) {
        contents.entries.forEach { (pos, tile) ->
            tile.drawOnto(surface, pos + offset)
        }
    }
}
