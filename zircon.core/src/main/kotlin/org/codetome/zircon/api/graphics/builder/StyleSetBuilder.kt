package org.codetome.zircon.api.graphics.builder

import org.codetome.zircon.api.Modifier
import org.codetome.zircon.api.builder.Builder
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.internal.factory.StyleSetFactory

/**
 * Builder used to create [StyleSet]s. Uses the default colors from
 * [TextColor]. Modifiers are empty by default.
 */
data class StyleSetBuilder(
        private var foregroundColor: TextColor = TextColor.defaultForegroundColor(),
        private var backgroundColor: TextColor = TextColor.defaultBackgroundColor(),
        private var modifiers: Set<Modifier> = setOf()) : Builder<StyleSet> {

    override fun build(): StyleSet = StyleSetFactory.create(
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
        fun newBuilder() = StyleSetBuilder()

        /**
         * Shorthand for the default character which is:
         * - a space character
         * - with default foreground
         * - and default background
         * - and no modifiers.
         */
        fun defaultStyle() = DEFAULT_STYLE

        /**
         * Shorthand for the empty style which has:
         * - transparent foreground
         * - and transparent background
         * - and no modifiers.
         */
        fun empty() = newBuilder()
                .backgroundColor(TextColor.transparent())
                .foregroundColor(TextColor.transparent())
                .modifiers(setOf())
                .build()

        val DEFAULT_STYLE = newBuilder().build()
    }
}
