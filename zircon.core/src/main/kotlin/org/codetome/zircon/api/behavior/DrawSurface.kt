package org.codetome.zircon.api.behavior

import org.codetome.zircon.api.data.Cell
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.util.Maybe

/**
 * Represents an object which can be drawn upon.
 * A [DrawSurface] is the most basic interface for all drawable surfaces
 * which exposes simple get and set functions for getting and setting
 * [Tile]s.
 */
interface DrawSurface : Boundable {

    /**
     * Returns the character stored at a particular position on this [DrawSurface].
     * Returns an empty [Maybe] if no [Tile] is present at the given [Position].
     */
    fun getTileAt(position: Position): Maybe<Tile>

    /**
     * Sets a [Tile] at a specific position in the [DrawSurface] to `tile`.
     * If the position is outside of the [DrawSurface]'s size, this method has no effect.
     * Note that if this [DrawSurface] already has the given [Tile] on the supplied [Position]
     * nothing will change.
     */
    fun setTileAt(position: Position, tile: Tile)

    /**
     * Sets a [Char] at a specific position in the [DrawSurface] to `char`.
     * If the position is outside of the [DrawSurface]'s size, this method has no effect.
     * Note that if the [DrawSurface] is [Styleable] its style information will be used.
     */
    fun setCharAt(position: Position, char: Char)

    /**
     * Creates a snapshot of the current state of this [DrawSurface].
     * A snapshot is useful to see a consistent state of a [DrawSurface]
     * regardless of potential changes by other threads.
     */
    fun createSnapshot(): List<Cell>

    /**
     * Draws a [Drawable] onto this [DrawSurface]. If the destination [DrawSurface] is larger than
     * this [Drawable], the areas outside of the area that is written to will be untouched.
     * @param offset the starting position of the drawing relative to the [DrawSurface]'s top left corner.
     */
    fun draw(drawable: Drawable,
             offset: Position = Position.topLeftCorner())
}
