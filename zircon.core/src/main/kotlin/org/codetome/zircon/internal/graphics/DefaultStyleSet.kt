package org.codetome.zircon.internal.graphics

import org.codetome.zircon.api.Modifier
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.color.TextColorFactory
import org.codetome.zircon.api.graphics.StyleSet

data class DefaultStyleSet(private var foregroundColor: TextColor = TextColorFactory.DEFAULT_FOREGROUND_COLOR,
                           private var backgroundColor: TextColor = TextColorFactory.DEFAULT_BACKGROUND_COLOR,
                           private val modifiers: Set<Modifier> = setOf()) : StyleSet {

    private val cacheKey = StringBuilder().apply {
        append(foregroundColor.generateCacheKey())
        append(backgroundColor.generateCacheKey())
        append(modifiers.joinToString(separator = "", transform = {it.generateCacheKey()}))
    }.toString()

    override fun getForegroundColor() = foregroundColor

    override fun getBackgroundColor() = backgroundColor

    override fun getModifiers() = modifiers

    override fun createCopy() = copy()

    override fun withBackgroundColor(backgroundColor: TextColor) = copy(backgroundColor = backgroundColor)

    override fun withForegroundColor(foregroundColor: TextColor) = copy(foregroundColor = foregroundColor)

    override fun withAddedModifiers(modifiers: Set<Modifier>) = copy(modifiers = this.modifiers.plus(modifiers))

    override fun withAddedModifiers(vararg modifiers: Modifier) = withAddedModifiers(modifiers.toSet())

    override fun withRemovedModifiers(modifiers: Set<Modifier>) = copy(modifiers = this.modifiers.minus(modifiers))

    override fun withRemovedModifiers(vararg modifiers: Modifier) = withRemovedModifiers(modifiers.toSet())

    override fun withModifiers(modifiers: Set<Modifier>) = copy(modifiers = modifiers)

    override fun withModifiers(vararg modifiers: Modifier) = withModifiers(modifiers.toSet())

    override fun withoutModifiers() = copy(modifiers = setOf())

    override fun generateCacheKey() = cacheKey
}