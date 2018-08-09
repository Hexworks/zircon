package org.hexworks.zircon.api.graphics

import org.hexworks.zircon.api.behavior.Cacheable
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.internal.graphics.DefaultStyleSet

/**
 * Represents style information which is handled by Zircon like
 * - background color
 * - foreground color and
 * - modifiers
 * and a set of useful operations on them.
 */
interface StyleSet : Cacheable {

    /**
     * Returns the foreground color.
     */
    fun foregroundColor(): TileColor

    /**
     * Returns the background color.
     */
    fun backgroundColor(): TileColor

    /**
     * Returns the modifiers.
     */
    fun modifiers(): Set<Modifier>

    /**
     * Returns a copy of the style information stored in this [StyleSet].
     */
    fun createCopy(): StyleSet

    /**
     * Creates a copy of this [StyleSet] with the given background color.
     */
    fun withBackgroundColor(backgroundColor: TileColor): StyleSet

    /**
     * Creates a copy of this [StyleSet] with the given foreground color.
     */
    fun withForegroundColor(foregroundColor: TileColor): StyleSet

    /**
     * Creates a copy of this [StyleSet] with the given modifiers added.
     */
    fun withAddedModifiers(modifiers: Set<Modifier>): StyleSet

    /**
     * Creates a copy of this [StyleSet] with the given modifiers added.
     */
    fun withAddedModifiers(vararg modifiers: Modifier): StyleSet

    /**
     * Creates a copy of this [StyleSet] with the given modifiers removed.
     */
    fun withRemovedModifiers(modifiers: Set<Modifier>): StyleSet

    /**
     * Creates a copy of this [StyleSet] with the given modifiers removed.
     */
    fun withRemovedModifiers(vararg modifiers: Modifier): StyleSet

    /**
     * Creates a copy of this [StyleSet] with the given modifiers.
     */
    fun withModifiers(modifiers: Set<Modifier>): StyleSet

    /**
     * Creates a copy of this [StyleSet] with the given modifiers.
     */
    fun withModifiers(vararg modifiers: Modifier): StyleSet

    /**
     * Creates a copy of this [StyleSet] with no modifiers.
     */
    fun withoutModifiers(): StyleSet

    companion object {

        /**
         * Shorthand for the default style which is:
         * - default foreground color (white)
         * - default background color (black)
         * - no modifiers
         */
        fun defaultStyle() = DEFAULT_STYLE

        /**
         * Shorthand for the empty style which has:
         * - transparent foreground
         * - and transparent background
         * - and no modifiers.
         */
        fun empty() = EMPTY

        /**
         * Creates a new [StyleSet].
         */
        fun create(foregroundColor: TileColor, backgroundColor: TileColor, modifiers: Set<Modifier> = setOf()): StyleSet {
            return DefaultStyleSet(
                    foregroundColor = foregroundColor,
                    backgroundColor = backgroundColor,
                    modifiers = modifiers)
        }

        private val DEFAULT_STYLE = DefaultStyleSet(
                foregroundColor = TileColor.defaultForegroundColor(),
                backgroundColor = TileColor.defaultBackgroundColor(),
                modifiers = setOf())

        private val EMPTY = DefaultStyleSet(
                foregroundColor = TileColor.transparent(),
                backgroundColor = TileColor.transparent(),
                modifiers = setOf())
    }

}
