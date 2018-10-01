package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.zircon.api.behavior.Styleable
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.Modifier

data class DefaultStyleable(private var styleSet: StyleSet) : Styleable {

    override fun toStyleSet() = styleSet

    override fun foregroundColor() = styleSet.foregroundColor()

    override fun backgroundColor() = styleSet.backgroundColor()

    override fun activeModifiers() = styleSet.modifiers()

    override fun setBackgroundColor(backgroundColor: TileColor) {
        styleSet = styleSet.withBackgroundColor(backgroundColor)
    }

    override fun setForegroundColor(foregroundColor: TileColor) {
        styleSet = styleSet.withForegroundColor(foregroundColor)
    }

    override fun enableModifiers(modifiers: Set<Modifier>) {
        styleSet = styleSet.withAddedModifiers(modifiers)
    }

    override fun enableModifiers(vararg modifiers: Modifier) = enableModifiers(modifiers.toSet())

    override fun disableModifiers(modifiers: Set<Modifier>) {
        styleSet = styleSet.withRemovedModifiers(modifiers)
    }

    override fun disableModifiers(vararg modifiers: Modifier) = disableModifiers(modifiers.toSet())

    override fun setModifiers(modifiers: Set<Modifier>) {
        styleSet = styleSet.withModifiers(modifiers)
    }

    override fun clearModifiers() {
        styleSet = styleSet.withoutModifiers()
    }

    override fun resetColorsAndModifiers() {
        styleSet = StyleSet.defaultStyle()
    }

    override fun setStyleFrom(source: StyleSet) {
        styleSet = source
    }
}
