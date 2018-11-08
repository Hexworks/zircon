package org.hexworks.zircon.internal.graphics

import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.Modifier

data class DefaultStyleSet(override val foregroundColor: TileColor = TileColor.defaultForegroundColor(),
                           override val backgroundColor: TileColor = TileColor.defaultBackgroundColor(),
                           override val modifiers: Set<Modifier> = setOf()) : StyleSet {

    private val cacheKey = "StyleSet(" +
            "fg=${foregroundColor.generateCacheKey()}," +
            "bg=${backgroundColor.generateCacheKey()}," +
            "m=[" + modifiers.joinToString(",") { it.generateCacheKey() } + "])"

    override fun generateCacheKey() = cacheKey

    override fun createCopy() = copy()

    override fun withForegroundColor(foregroundColor: TileColor) = copy(foregroundColor = foregroundColor)

    override fun withBackgroundColor(backgroundColor: TileColor) = copy(backgroundColor = backgroundColor)

    override fun withModifiers(modifiers: Set<Modifier>) = copy(modifiers = modifiers)

    override fun withAddedModifiers(modifiers: Set<Modifier>) = copy(modifiers = this.modifiers.plus(modifiers))

    override fun withRemovedModifiers(modifiers: Set<Modifier>) = copy(modifiers = this.modifiers.minus(modifiers))

    override fun withNoModifiers() = copy(modifiers = setOf())

}
