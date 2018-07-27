package org.codetome.zircon.poc.drawableupgrade.drawables

import org.codetome.zircon.poc.drawableupgrade.Position
import org.codetome.zircon.poc.drawableupgrade.Tile
import java.util.*
import java.util.concurrent.Executors

/**
 * this is a basic building block which can be re-used by complex image
 * classes like layers, boxes, components, and more
 * all classes which are implementing the DrawSurface or the Drawable operations can
 * use this class as a base class just like how the TileGrid uses it
 */

data class ThreadedTileImage(val width: Int, val height: Int) : TileImage {

    private val contents = mutableMapOf<Position, Tile>()

    // we make sure that all operations on this object are serialized
    private val executor = Executors.newSingleThreadExecutor()

    override fun getTileAt(position: Position): Optional<Tile> {
        return Optional.ofNullable(executor.submit<Tile?> { contents[position] }.get())
    }

    override fun setTileAt(position: Position, tile: Tile) {

        // this is the only place where writing happens
        // we don't need `get` here because read operations are also
        // serialized
        executor.submit {

            // can't set a tile if it is out of bounds
            if (position.x < width && position.y < height) {
                contents[position] = tile
            }
        }
    }

    override fun createSnapshot(): Map<Position, Tile> {
        return executor.submit<Map<Position, Tile>> {
            contents.map { Pair(it.key, it.value) }.toMap()
        }.get()
    }

    override fun draw(drawable: Drawable, offset: Position) {

        // we call drawOnto purposefully since it handles the details of how
        // the actual drawing happens
        // the draw function is just a convenience for the users
        // therefore no executor call is needed here
        drawable.drawOnto(this, offset)
    }

    override fun drawOnto(surface: DrawSurface, offset: Position) {
        executor.submit {
            contents.entries.forEach { (pos, tile) ->

                // drawing an arbitrarily complex Drawable onto an
                // arbitrary DrawSurface is always reduced to drawing tiles since they are
                // the atomic building blocks of any DrawSurface / Drawable
                tile.drawOnto(surface, pos + offset)
            }
        }
    }
}
