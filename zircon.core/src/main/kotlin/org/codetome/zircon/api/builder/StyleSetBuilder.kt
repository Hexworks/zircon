package org.codetome.zircon.api.builder

import org.codetome.zircon.api.Modifier
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.color.TextColorFactory
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.graphics.StyleSetCompanion
import org.codetome.zircon.api.graphics.StyleSetFactory

/**
 * Builder used to create [StyleSet]s. Uses the default colors from
 * [TextColorFactory]. Modifiers are empty by default.
 */
data class StyleSetBuilder(
        private var foregroundColor: TextColor = TextColorFactory.defaultForegroundColor(),
        private var backgroundColor: TextColor = TextColorFactory.defaultBackgroundColor(),
        private var modifiers: Set<Modifier> = setOf()) : Builder<StyleSet> {

    override fun build(): StyleSet = StyleSetFactory.create(
            foregroundColor,
            backgroundColor,
            modifiers.toMutableSet())

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

    companion object : StyleSetCompanion
}
