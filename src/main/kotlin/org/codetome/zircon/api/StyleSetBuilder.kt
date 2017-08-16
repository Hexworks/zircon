package org.codetome.zircon.api

import org.codetome.zircon.Modifier
import org.codetome.zircon.color.TextColor
import org.codetome.zircon.graphics.style.DefaultStyleSet

class StyleSetBuilder {

    private var foregroundColor: TextColor = TextColorFactory.DEFAULT_FOREGROUND_COLOR
    private var backgroundColor: TextColor = TextColorFactory.DEFAULT_BACKGROUND_COLOR
    private var modifiers: Set<Modifier> = setOf()

    fun foregroundColor(foregroundColor: TextColor) = also {
        this.foregroundColor = foregroundColor
    }

    fun backgroundColor(backgroundColor: TextColor) = also {
        this.backgroundColor = backgroundColor
    }

    fun modifiers(modifiers: Set<Modifier>) = also {
        this.modifiers = modifiers.toSet()
    }

    fun modifier(vararg modifiers: Modifier) = also {
        this.modifiers = modifiers.toSet()
    }

    fun build() = DefaultStyleSet(
            foregroundColor = foregroundColor,
            backgroundColor = backgroundColor,
            modifiers = modifiers.toSet())

    companion object {

        /**
         * Creates a new [StyleSetBuilder] for creating [org.codetome.zircon.graphics.style.StyleSet]s.
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
        val EMPTY = StyleSetBuilder.newBuilder()
                .backgroundColor(TextColorFactory.TRANSPARENT)
                .foregroundColor(TextColorFactory.TRANSPARENT)
                .modifiers(setOf())
                .build()
    }
}