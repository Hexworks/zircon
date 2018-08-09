package org.hexworks.zircon.api.builder.graphics

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.Modifier

/**
 * Builder used to create [StyleSet]s. Uses the default colors from
 * [TileColor]. Modifiers are empty by default.
 */
data class StyleSetBuilder(
        private var foregroundColor: TileColor = StyleSet.defaultStyle().foregroundColor(),
        private var backgroundColor: TileColor = StyleSet.defaultStyle().backgroundColor(),
        private var modifiers: Set<Modifier> = StyleSet.defaultStyle().modifiers()) : Builder<StyleSet> {

    override fun build(): StyleSet = StyleSet.create(
            foregroundColor = foregroundColor,
            backgroundColor = backgroundColor,
            modifiers = modifiers.toMutableSet())

    override fun createCopy() = copy(
            modifiers = modifiers.toSet())


    fun foregroundColor(foregroundColor: TileColor) = also {
        this.foregroundColor = foregroundColor
    }

    fun backgroundColor(backgroundColor: TileColor) = also {
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
         * Creates a new [StyleSetBuilder] for creating [org.hexworks.zircon.api.graphics.StyleSet]s.
         */
        fun newBuilder() = StyleSetBuilder()
    }
}
