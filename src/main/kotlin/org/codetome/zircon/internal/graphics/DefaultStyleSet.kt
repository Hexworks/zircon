package org.codetome.zircon.internal.graphics

import org.codetome.zircon.api.Modifier
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.factory.TextColorFactory
import org.codetome.zircon.api.graphics.StyleSet
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicReference

open class DefaultStyleSet(
        foregroundColor: TextColor = TextColorFactory.DEFAULT_FOREGROUND_COLOR,
        backgroundColor: TextColor = TextColorFactory.DEFAULT_BACKGROUND_COLOR,
        modifiers: Set<Modifier> = setOf()
) : StyleSet {

    private var foregroundColor = AtomicReference<TextColor>(foregroundColor)
    private var backgroundColor = AtomicReference<TextColor>(backgroundColor)
    private val modifiers = ConcurrentHashMap<Modifier, Unit>()

    init {
        modifiers.forEach {
            this.modifiers.put(it, Unit)
        }
    }

    override fun toStyleSet() = DefaultStyleSet(
            foregroundColor = foregroundColor.get(),
            backgroundColor = backgroundColor.get(),
            modifiers = modifiers.keys.toSet())

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
        foregroundColor.set(TextColorFactory.DEFAULT_FOREGROUND_COLOR)
        backgroundColor.set(TextColorFactory.DEFAULT_BACKGROUND_COLOR)
    }

    @Synchronized
    override fun setStyleFrom(source: StyleSet) {
        setBackgroundColor(source.getBackgroundColor())
        setForegroundColor(source.getForegroundColor())
        setModifiers(source.getActiveModifiers().toSet())
    }

}