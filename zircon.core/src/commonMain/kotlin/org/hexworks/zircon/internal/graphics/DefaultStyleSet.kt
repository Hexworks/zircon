package org.hexworks.zircon.internal.graphics

import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.Modifier

data class DefaultStyleSet internal constructor(
    override val foregroundColor: TileColor = TileColor.defaultForegroundColor(),
    override val backgroundColor: TileColor = TileColor.defaultBackgroundColor(),
    override val modifiers: Set<Modifier> = setOf()
) : StyleSet {

    override val cacheKey: String
        get() = "StyleSet(" +
                "fg=${foregroundColor.cacheKey}," +
                "bg=${backgroundColor.cacheKey}," +
                "m=[" + modifiers.joinToString(",") { it.cacheKey } + "])"

    override fun createCopy() = copy()

    override fun withForegroundColor(foregroundColor: TileColor) = copy(foregroundColor = foregroundColor)

    override fun withBackgroundColor(backgroundColor: TileColor) = copy(backgroundColor = backgroundColor)

    override fun withModifiers(modifiers: Set<Modifier>) = copy(modifiers = modifiers)

    override fun withAddedModifiers(modifiers: Set<Modifier>) = copy(modifiers = this.modifiers.plus(modifiers))

    override fun withRemovedModifiers(modifiers: Set<Modifier>) = copy(modifiers = this.modifiers.minus(modifiers))

    override fun withNoModifiers() = copy(modifiers = setOf())

}
