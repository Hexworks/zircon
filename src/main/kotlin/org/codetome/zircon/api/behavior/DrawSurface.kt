package org.codetome.zircon.api.behavior

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.TextCharacter
import java.util.*

/**
 * Represents an object which can be drawn upon.
 * A [DrawSurface] is the most basic interface for all drawable surfaces
 * which exposes simple get and set functions for getting and setting
 * [TextCharacter]s.
 */
interface DrawSurface : Boundable {

    /**
     * Returns the character stored at a particular position on this [DrawSurface].
     * Returns an empty [Optional] if no [TextCharacter] is present at the given [Position].
     */
    fun getCharacterAt(position: Position): Optional<TextCharacter>

    /**
     * Sets the character at a specific position in the [DrawSurface] to a particular [TextCharacter].
     * If the position is outside of the [DrawSurface]'s size, this method has no side effect.
     * Note that if this [DrawSurface] already has the given [TextCharacter] on the supplied [Position]
     * nothing will change and this method will return `false`.
     * @return true if the character was set, false if the position is outside of the [DrawSurface]
     * or if no change happened.
     */
    fun setCharacterAt(position: Position, character: TextCharacter): Boolean

    /**
     * Sets the character at a specific position in the [DrawSurface] to a particular [TextCharacter].
     * If the position is outside of the [DrawSurface]'s size, this method has no side effect.
     * **Note that** this method will use the style information if the [DrawSurface] implements
     * [org.codetome.zircon.api.graphics.StyleSet].
     * If not it will use [org.codetome.zircon.api.builder.TextCharacterBuilder.DEFAULT_CHARACTER]
     * when it sets the given `character` as a [TextCharacter].
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
             offset: Position = Position.TOP_LEFT_CORNER)
}