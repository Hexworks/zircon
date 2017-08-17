package org.codetome.zircon

import java.util.*

/**
 * Represents an object which can be drawn upon.
 * A [DrawSurface] is the most basic interface for all drawable surfaces
 * which exposes simple get and set functions for getting and setting
 * [TextCharacter]s.
 */
interface DrawSurface {

    /**
     * Returns the character stored at a particular position in this image
     */
    fun getCharacterAt(position: Position): Optional<TextCharacter>

    /**
     * Sets the character at a specific position in the [DrawSurface] to a particular [TextCharacter].
     * If the position is outside of the [DrawSurface]'s size, this method has no side effect.
     * @return true if the character was set, false if the position is outside of the [DrawSurface].
     */
    fun setCharacterAt(position: Position, character: TextCharacter): Boolean
}