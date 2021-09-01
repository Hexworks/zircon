package org.hexworks.zircon.api.graphics

import org.hexworks.zircon.api.behavior.Cacheable
import org.hexworks.zircon.api.behavior.Copiable
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.internal.graphics.DefaultStyleSet
import kotlin.jvm.JvmStatic

/**
 * Represents style information which is handled by Zircon like
 * - background color
 * - foreground color and
 * - modifiers
 * and a set of useful operations on them.
 */
@Suppress("JVM_STATIC_IN_INTERFACE_1_6")
interface StyleSet : Cacheable, Copiable<StyleSet> {

    val foregroundColor: TileColor
    val backgroundColor: TileColor
    val modifiers: Set<Modifier>

    /**
     * Creates a copy of this [StyleSet] with the given foreground color.
     */
    fun withForegroundColor(foregroundColor: TileColor): StyleSet

    /**
     * Creates a copy of this [StyleSet] with the given background color.
     */
    fun withBackgroundColor(backgroundColor: TileColor): StyleSet

    /**
     * Creates a copy of this [StyleSet] with the given modifiers.
     */
    fun withModifiers(modifiers: Set<Modifier>): StyleSet

    /**
     * Creates a copy of this [StyleSet] with the given modifiers.
     */
    fun withModifiers(vararg modifiers: Modifier): StyleSet =
        withModifiers(modifiers.toSet())

    /**
     * Creates a copy of this [StyleSet] with the given modifiers added.
     */
    fun withAddedModifiers(modifiers: Set<Modifier>): StyleSet

    /**
     * Creates a copy of this [StyleSet] with the given modifiers added.
     */
    fun withAddedModifiers(vararg modifiers: Modifier): StyleSet =
        withAddedModifiers(modifiers.toSet())

    /**
     * Creates a copy of this [StyleSet] with the given modifiers removed.
     */
    fun withRemovedModifiers(modifiers: Set<Modifier>): StyleSet

    /**
     * Creates a copy of this [StyleSet] with the given modifiers removed.
     */
    fun withRemovedModifiers(vararg modifiers: Modifier): StyleSet =
        withRemovedModifiers(modifiers.toSet())

    /**
     * Creates a copy of this [StyleSet] with no modifiers.
     */
    fun withNoModifiers(): StyleSet

    companion object {

        /**
         * Shorthand for the default style which is:
         * - default foreground color (white)
         * - default background color (black)
         * - no modifiers
         */
        @JvmStatic
        fun defaultStyle() = DEFAULT_STYLE

        /**
         * Shorthand for the empty style which has:
         * - transparent foreground
         * - and transparent background
         * - and no modifiers.
         */
        @JvmStatic
        fun empty() = EMPTY

        /**
         * Creates a new [StyleSetBuilder] for creating [org.hexworks.zircon.api.graphics.StyleSet]s.
         */
        @JvmStatic
        fun newBuilder() = StyleSetBuilder.newBuilder()

        private val DEFAULT_STYLE = DefaultStyleSet(
            foregroundColor = TileColor.defaultForegroundColor(),
            backgroundColor = TileColor.defaultBackgroundColor(),
            modifiers = setOf()
        )

        private val EMPTY = DefaultStyleSet(
            foregroundColor = TileColor.transparent(),
            backgroundColor = TileColor.transparent(),
            modifiers = setOf()
        )
    }

}
