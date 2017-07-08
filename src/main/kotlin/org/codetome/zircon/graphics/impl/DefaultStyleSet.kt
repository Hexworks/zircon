package org.codetome.zircon.graphics.impl

import org.codetome.zircon.Modifier
import org.codetome.zircon.TextColor
import org.codetome.zircon.graphics.StyleSet
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicReference

open class DefaultStyleSet : StyleSet {

    private var foregroundColor = AtomicReference<TextColor>(TextColor.DEFAULT_FOREGROUND_COLOR)
    private var backgroundColor = AtomicReference<TextColor>(TextColor.DEFAULT_BACKGROUND_COLOR)
    private val modifiers = ConcurrentHashMap<Modifier, Any>()

    override fun getForegroundColor(): TextColor = foregroundColor.get()

    override fun getBackgroundColor(): TextColor = backgroundColor.get()

    override fun getActiveModifiers() = modifiers.keys.toSet()

    override fun setBackgroundColor(backgroundColor: TextColor) {
        this.backgroundColor.set(backgroundColor)
    }

    override fun setForegroundColor(foregroundColor: TextColor) {
        this.foregroundColor.set(foregroundColor)
    }

    override fun enableModifiers(vararg modifiers: Modifier) {
        this.modifiers.putAll(modifiers.map { Pair(it, Unit) })
    }

    override fun enableModifier(modifier: Modifier) {
        enableModifiers(modifier)
    }


    @Synchronized
    override fun disableModifiers(vararg modifiers: Modifier) {
        modifiers.forEach {
            this.modifiers.remove(it)
        }
    }

    override fun disableModifier(modifier: Modifier) {
        disableModifiers(modifier)
    }

    @Synchronized
    override fun setModifiers(modifiers: Set<Modifier>) {
        this.modifiers.clear()
        enableModifiers(*modifiers.toTypedArray())
    }

    override fun clearModifiers() {
        this.modifiers.clear()
    }

    @Synchronized
    override fun resetColorsAndModifiers() {
        modifiers.clear()
        foregroundColor.set(TextColor.DEFAULT_FOREGROUND_COLOR)
        backgroundColor.set(TextColor.DEFAULT_BACKGROUND_COLOR)
    }

    @Synchronized
    override fun setStyleFrom(source: StyleSet) {
        setBackgroundColor(source.getBackgroundColor())
        setForegroundColor(source.getForegroundColor())
        setModifiers(source.getActiveModifiers().toSet())
    }

}