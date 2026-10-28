package org.hexworks.zircon.api.graphics

import org.hexworks.zircon.api.behavior.Cacheable
import org.hexworks.zircon.api.behavior.Copiable
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.internal.graphics.DefaultStyleSet

/**
 * Represents style information that is handled by Zircon like
 * - background color
 * - foreground color and
 * - modifiers
 * and a set of useful operations on them.
 */
//! TODO: rename this to something better to finally remove the Lanterna legacy naming
public interface StyleSet : Cacheable, Copiable<StyleSet> {

    public val foregroundColor: Color
    public val backgroundColor: Color
    public val modifiers: Set<Modifier>

    /**
     * Creates a copy of this [StyleSet] with the given foreground color.
     */
    public fun withForegroundColor(foregroundColor: Color): StyleSet

    /**
     * Creates a copy of this [StyleSet] with the given background color.
     */
    public fun withBackgroundColor(backgroundColor: Color): StyleSet

    /**
     * Creates a copy of this [StyleSet] with the given modifiers.
     */
    public fun withModifiers(modifiers: Set<Modifier>): StyleSet

    /**
     * Creates a copy of this [StyleSet] with the given modifiers.
     */
    public fun withModifiers(vararg modifiers: Modifier): StyleSet =
        withModifiers(modifiers.toSet())

    /**
     * Creates a copy of this [StyleSet] with the given modifiers added.
     */
    public fun withAddedModifiers(modifiers: Set<Modifier>): StyleSet

    /**
     * Creates a copy of this [StyleSet] with the given modifiers added.
     */
    public fun withAddedModifiers(vararg modifiers: Modifier): StyleSet =
        withAddedModifiers(modifiers.toSet())

    /**
     * Creates a copy of this [StyleSet] with the given modifiers removed.
     */
    public fun withRemovedModifiers(modifiers: Set<Modifier>): StyleSet

    /**
     * Creates a copy of this [StyleSet] with the given modifiers removed.
     */
    public fun withRemovedModifiers(vararg modifiers: Modifier): StyleSet =
        withRemovedModifiers(modifiers.toSet())

    /**
     * Creates a copy of this [StyleSet] with no modifiers.
     */
    public fun withNoModifiers(): StyleSet

    public companion object {

        /**
         * Shorthand for the default style which is:
         * - default foreground color (white)
         * - default background color (black)
         * - no modifiers
         */
        public fun defaultStyle(): StyleSet = DEFAULT_STYLE

        /**
         * Shorthand for the empty style which has:
         * - transparent foreground
         * - and transparent background
         * - and no modifiers.
         */
        public fun empty(): StyleSet = EMPTY

        private val DEFAULT_STYLE: StyleSet = DefaultStyleSet(
            foregroundColor = Color.DEFAULT_FOREGROUND_COLOR,
            backgroundColor = Color.DEFAULT_BACKGROUND_COLOR,
            modifiers = setOf()
        )

        private val EMPTY: StyleSet = DefaultStyleSet(
            foregroundColor = Color.TRANSPARENT,
            backgroundColor = Color.TRANSPARENT,
            modifiers = setOf()
        )
    }

}
