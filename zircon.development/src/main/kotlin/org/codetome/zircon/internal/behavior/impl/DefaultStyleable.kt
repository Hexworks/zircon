package org.codetome.zircon.internal.behavior.impl

import org.codetome.zircon.api.behavior.Styleable
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.modifier.Modifier

data class DefaultStyleable(private var styleSet: StyleSet) : Styleable {

    override fun toStyleSet() = styleSet.createCopy()

    override fun getForegroundColor() = styleSet.getForegroundColor()

    override fun getBackgroundColor() = styleSet.getBackgroundColor()

    override fun getActiveModifiers() = styleSet.getModifiers()

    override fun setBackgroundColor(backgroundColor: TextColor) {
        styleSet = styleSet.withBackgroundColor(backgroundColor)
    }

    override fun setForegroundColor(foregroundColor: TextColor) {
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
