package org.codetome.zircon.api.builder.graphics

import org.codetome.zircon.api.builder.Builder
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.modifier.Modifier

/**
 * Builder used to create [StyleSet]s. Uses the default colors from
 * [TextColor]. Modifiers are empty by default.
 */
data class StyleSetBuilder(
        private var foregroundColor: TextColor = StyleSet.defaultStyle().getForegroundColor(),
        private var backgroundColor: TextColor = StyleSet.defaultStyle().getBackgroundColor(),
        private var modifiers: Set<Modifier> = StyleSet.defaultStyle().getModifiers()) : Builder<StyleSet> {

    override fun build(): StyleSet = StyleSet.create(
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
    }
}
