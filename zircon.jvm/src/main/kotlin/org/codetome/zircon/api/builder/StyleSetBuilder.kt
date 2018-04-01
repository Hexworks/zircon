package org.codetome.zircon.api.builder

import org.codetome.zircon.api.Modifier
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.color.TextColorFactory
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.internal.graphics.DefaultStyleSet

/**
 * Builder used to create [StyleSet]s. Uses the default colors from
 * [TextColorFactory]. Modifiers are empty by default.
 */
data class StyleSetBuilder(
        private var foregroundColor: TextColor = TextColorFactory.DEFAULT_FOREGROUND_COLOR,
        private var backgroundColor: TextColor = TextColorFactory.DEFAULT_BACKGROUND_COLOR,
        private var modifiers: Set<Modifier> = setOf()) : Builder<StyleSet> {

    override fun build(): StyleSet = DefaultStyleSet(
            foregroundColor = foregroundColor,
            backgroundColor = backgroundColor,
            modifiers = modifiers.toMutableSet())

    override fun createCopy() = copy(
            modifiers = modifiers.toSet())


    fun foregroundColor(foregroundColor: TextColor) = also {
        this.foregroundColor = foregroundColor
    }

    fun backgroundColor(backgroundColor: TextColor) = also {
        this.backgroundColor = backgroundColor
    }

    fun modifiers(modifiers: Set<Modifier>) = also {
        this.modifiers = modifiers.toSet()
    }

    fun modifiers(vararg modifiers: Modifier) = also {
        this.modifiers = modifiers.toSet()
    }

    companion object {

        /**
         * Creates a new [StyleSetBuilder] for creating [org.codetome.zircon.api.graphics.StyleSet]s.
         */
        @JvmStatic
        fun newBuilder() = StyleSetBuilder()

        /**
         * Shorthand for the default character which is:
         * - a space character
         * - with default foreground
         * - and default background
         * - and no modifiers.
         */
        @JvmField
        val DEFAULT_STYLE = newBuilder().build()

        /**
         * Shorthand for the empty style which has:
         * - transparent foreground
         * - and transparent background
         * - and no modifiers.
         */
        @JvmField
        val EMPTY = newBuilder()
                .backgroundColor(TextColorFactory.TRANSPARENT)
                .foregroundColor(TextColorFactory.TRANSPARENT)
                .modifiers(setOf())
                .build()
    }
}
