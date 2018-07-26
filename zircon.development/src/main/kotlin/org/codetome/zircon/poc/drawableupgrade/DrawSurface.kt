package org.codetome.zircon.poc.drawableupgrade

import java.util.*

/**
 * A draw surface is like a piece of paper. Any [Drawable] can be drawn on top of it
 * It also supports drawing the most basic building block: a [Tile]. This will be useful
 * later, see the implementation in [TileImage]
 */
interface DrawSurface {

    /**
     * Returns the stored [Tile] at the given [Position] (if any).
     */
    fun getTileAt(position: Position): Optional<Tile>

    /**
     * Sets the given [Tile] at the given [Position].
     */
    fun setTileAt(position: Position, tile: Tile)

    /**
     * Creates a snapshot of the current state of this [DrawSurface].
     * A snapshot is useful to see a consistent state of an image
     * like creating a picture.
     */

    fun createSnapshot(): Map<Position, Tile>

    /**
     * Draws the given [Drawable] onto this [DrawSurface] at the given `offset` [Position].
     */
    fun draw(drawable: Drawable,
             offset: Position)
}
