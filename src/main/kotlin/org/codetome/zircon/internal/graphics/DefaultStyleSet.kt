package org.codetome.zircon.internal.graphics

import org.codetome.zircon.api.Modifier
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.color.TextColorFactory
import org.codetome.zircon.api.graphics.StyleSet

data class DefaultStyleSet(
        private var foregroundColor: TextColor = TextColorFactory.DEFAULT_FOREGROUND_COLOR,
        private var backgroundColor: TextColor = TextColorFactory.DEFAULT_BACKGROUND_COLOR,
        private val modifiers: MutableSet<Modifier> = mutableSetOf()
) : StyleSet {

    init {
        modifiers.forEach {
            this.modifiers.add(it)
        }
    }

    override fun toStyleSet() = DefaultStyleSet(
            foregroundColor = foregroundColor,
            backgroundColor = backgroundColor,
            modifiers = modifiers.toMutableSet())

    override fun getForegroundColor(): TextColor = foregroundColor

    override fun getBackgroundColor(): TextColor = backgroundColor

    override fun getActiveModifiers() = modifiers.toSet()

    override fun setBackgroundColor(backgroundColor: TextColor) {
        this.backgroundColor = backgroundColor
    }

    override fun setForegroundColor(foregroundColor: TextColor) {
        this.foregroundColor = foregroundColor
    }

    override fun enableModifiers(modifiers: Set<Modifier>) {
        this.modifiers.addAll(modifiers)
    }

    override fun enableModifier(modifier: Modifier) {
        modifiers.add(modifier)
    }

    @Synchronized
    override fun disableModifiers(modifiers: Set<Modifier>) {
        this.modifiers.removeAll(modifiers)
    }

    override fun disableModifier(modifier: Modifier) {
        this.modifiers.remove(modifier)
    }

    @Synchronized
    override fun setModifiers(modifiers: Set<Modifier>) {
        this.modifiers.clear()
        enableModifiers(modifiers)
    }

    override fun clearModifiers() {
        this.modifiers.clear()
    }

    @Synchronized
    override fun resetColorsAndModifiers() {
        modifiers.clear()
        foregroundColor = TextColorFactory.DEFAULT_FOREGROUND_COLOR
        backgroundColor = TextColorFactory.DEFAULT_BACKGROUND_COLOR
    }

    @Synchronized
    override fun setStyleFrom(source: StyleSet) {
        setBackgroundColor(source.getBackgroundColor())
        setForegroundColor(source.getForegroundColor())
        setModifiers(source.getActiveModifiers().toSet())
    }
}