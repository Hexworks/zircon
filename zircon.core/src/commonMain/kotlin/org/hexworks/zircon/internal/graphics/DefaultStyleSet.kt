package org.hexworks.zircon.internal.graphics

import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.Modifier

public data class DefaultStyleSet(
    override val foregroundColor: Color = Color.DEFAULT_FOREGROUND_COLOR,
    override val backgroundColor: Color = Color.DEFAULT_BACKGROUND_COLOR,
    override val modifiers: Set<Modifier> = setOf()
) : StyleSet {

    override val cacheKey: String
        get() = "StyleSet(" +
                "fg=${foregroundColor.cacheKey}," +
                "bg=${backgroundColor.cacheKey}," +
                "m=[" + modifiers.joinToString(",") { it.cacheKey } + "])"

    override fun createCopy(): DefaultStyleSet = copy()

    override fun withForegroundColor(foregroundColor: Color): StyleSet = copy(foregroundColor = foregroundColor)

    override fun withBackgroundColor(backgroundColor: Color): StyleSet = copy(backgroundColor = backgroundColor)

    override fun withModifiers(modifiers: Set<Modifier>): StyleSet = copy(modifiers = modifiers)

    override fun withAddedModifiers(modifiers: Set<Modifier>): StyleSet = copy(modifiers = this.modifiers.plus(modifiers))

    override fun withRemovedModifiers(modifiers: Set<Modifier>): StyleSet = copy(modifiers = this.modifiers.minus(modifiers))

    override fun withNoModifiers(): StyleSet = copy(modifiers = setOf())

}
