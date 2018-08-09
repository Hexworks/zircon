package org.hexworks.zircon.internal.graphics

import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.Modifier

data class DefaultStyleSet(private var foregroundColor: TileColor = TileColor.defaultForegroundColor(),
                           private var backgroundColor: TileColor = TileColor.defaultBackgroundColor(),
                           private val modifiers: Set<Modifier> = setOf()) : StyleSet {

    private val cacheKey = "StyleSet(" +
            "fg=${foregroundColor.generateCacheKey()}," +
            "bg=${backgroundColor.generateCacheKey()}," +
            "m=[" + modifiers.joinToString(",") { it.generateCacheKey() } + "])"

    override fun generateCacheKey() = cacheKey

    override fun foregroundColor() = foregroundColor

    override fun backgroundColor() = backgroundColor

    override fun modifiers() = modifiers

    override fun createCopy() = copy()

    override fun withBackgroundColor(backgroundColor: TileColor) = copy(backgroundColor = backgroundColor)

    override fun withForegroundColor(foregroundColor: TileColor) = copy(foregroundColor = foregroundColor)

    override fun withAddedModifiers(modifiers: Set<Modifier>) = copy(modifiers = this.modifiers.plus(modifiers))

    override fun withAddedModifiers(vararg modifiers: Modifier) = withAddedModifiers(modifiers.toSet())

    override fun withRemovedModifiers(modifiers: Set<Modifier>) = copy(modifiers = this.modifiers.minus(modifiers))

    override fun withRemovedModifiers(vararg modifiers: Modifier) = withRemovedModifiers(modifiers.toSet())

    override fun withModifiers(modifiers: Set<Modifier>) = copy(modifiers = modifiers)

    override fun withModifiers(vararg modifiers: Modifier) = withModifiers(modifiers.toSet())

    override fun withoutModifiers() = copy(modifiers = setOf())

}
