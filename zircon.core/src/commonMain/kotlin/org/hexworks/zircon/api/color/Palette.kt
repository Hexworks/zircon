package org.hexworks.zircon.api.color

/**
 * A [Palette] is a collection of [Color]s that can be identified
 * by their unique name (key).
 */
interface Palette<E : Enum<E>> {
    /**
     * Lists all the names of the colors in this palette.
     */
    val keys: Set<E>

    /**
     * Lists all the colors in this palette.
     */
    val colors: Set<Color>

    /**
     * Returns the [Color] for the given `key`.
     */
    operator fun get(key: E): Color
}