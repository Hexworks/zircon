package org.codetome.zircon.internal.behavior.impl

import org.codetome.zircon.api.Modifier
import org.codetome.zircon.api.behavior.Styleable
import org.codetome.zircon.api.builder.StyleSetBuilder
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.graphics.StyleSet
import java.util.concurrent.atomic.AtomicReference

data class DefaultStyleable(private var styleSet: AtomicReference<StyleSet>) : Styleable {

    override fun toStyleSet() = styleSet.get().createCopy()

    override fun getForegroundColor() = styleSet.get().getForegroundColor()

    override fun getBackgroundColor() = styleSet.get().getBackgroundColor()

    override fun getActiveModifiers() = styleSet.get().getModifiers()

    override fun setBackgroundColor(backgroundColor: TextColor) {
        styleSet.set(styleSet.get().withBackgroundColor(backgroundColor))
    }

    override fun setForegroundColor(foregroundColor: TextColor) {
        styleSet.set(styleSet.get().withForegroundColor(foregroundColor))
    }

    override fun enableModifiers(modifiers: Set<Modifier>) {
        styleSet.set(styleSet.get().withAddedModifiers(modifiers))
    }

    override fun enableModifiers(vararg modifiers: Modifier) = enableModifiers(modifiers.toSet())

    override fun disableModifiers(modifiers: Set<Modifier>) {
        styleSet.set(styleSet.get().withRemovedModifiers(modifiers))
    }

    override fun disableModifiers(vararg modifiers: Modifier) = disableModifiers(modifiers.toSet())

    override fun setModifiers(modifiers: Set<Modifier>) {
        styleSet.set(styleSet.get().withModifiers(modifiers))
    }

    override fun clearModifiers() {
        styleSet.set(styleSet.get().withoutModifiers())
    }

    @Synchronized
    override fun resetColorsAndModifiers() {
        styleSet.set(StyleSetBuilder.defaultStyle())
    }

    @Synchronized
    override fun setStyleFrom(source: StyleSet) {
        styleSet.set(source)
    }
}
