package org.codetome.zircon.api.behavior

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
    fun getCharacterAt(position: Position): Maybe<Tile>

    /**
     * Sets the character at a specific position in the [DrawSurface] to a particular [Tile].
     * If the position is outside of the [DrawSurface]'s size, this method has no effect.
     * Note that if this [DrawSurface] already has the given [Tile] on the supplied [Position]
     * nothing will change and this method will return `false`.
     *
     * @return true if the character was set, false if the position is outside of the [DrawSurface]
     * or if no change happened.
     */
    fun setCharacterAt(position: Position, character: Tile): Boolean

    /**
     * Sets the character at a specific position in the [DrawSurface] to a particular [Tile].
     * If the position is outside of the [DrawSurface]'s size, this method has no effect.
     * **Note that** this method will use the style information if the [DrawSurface] implements
     * [org.codetome.zircon.api.graphics.StyleSet].
     *
     * If not it will use [org.codetome.zircon.api.TextCharacter.defaultCharacter]
     * when it sets the given `character` as a [Tile].
     *
     * @return true if the character was set, false if the position is outside of the [DrawSurface]
     * or if no change happened.
     */
    fun setCharacterAt(position: Position, character: Char): Boolean

    /**
     * Draws a [Drawable] onto this [DrawSurface]. If the destination [DrawSurface] is larger than
     * this [Drawable], the areas outside of the area that is written to will be untouched.
     * @param offset the starting position of the drawing relative to the [DrawSurface]'s top left corner.
     */
    fun draw(drawable: Drawable,
             offset: Position = Position.topLeftCorner())
}
