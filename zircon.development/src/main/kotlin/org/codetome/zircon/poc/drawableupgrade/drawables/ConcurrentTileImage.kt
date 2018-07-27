package org.codetome.zircon.poc.drawableupgrade.drawables

import com.romix.scala.collection.concurrent.TrieMap
import org.codetome.zircon.poc.drawableupgrade.Position
import org.codetome.zircon.poc.drawableupgrade.Tile
import java.util.*
import java.util.concurrent.ConcurrentHashMap

/**
 * this is a basic building block which can be re-used by complex image
 * classes like layers, boxes, components, and more
 * all classes which are implementing the DrawSurface or the Drawable operations can
 * use this class as a base class just like how the TileGrid uses it
 */

data class ConcurrentTileImage(val width: Int, val height: Int) : TileImage {

    private val contents = ConcurrentHashMap<Position, Tile>()

    override fun getTileAt(position: Position): Optional<Tile> {
        return Optional.ofNullable(contents[position])
    }

    override fun setTileAt(position: Position, tile: Tile) {
        if (position.x < width && position.y < height) {
            contents[position] = tile
        }
    }

    override fun createSnapshot(): Map<Position, Tile> {
        return contents
    }

    override fun draw(drawable: Drawable, offset: Position) {
        drawable.drawOnto(this, offset)
    }

    override fun drawOnto(surface: DrawSurface, offset: Position) {
        contents.entries.forEach { (pos, tile) ->
            tile.drawOnto(surface, pos + offset)
        }
    }
}
