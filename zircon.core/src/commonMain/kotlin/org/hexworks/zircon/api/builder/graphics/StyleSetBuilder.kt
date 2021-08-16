package org.hexworks.zircon.api.builder.graphics

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.internal.graphics.DefaultStyleSet
import kotlin.jvm.JvmStatic

/**
 * Builder used to create [StyleSet]s. Uses the default colors from
 * [TileColor]. Modifiers are empty by default.
 */
class StyleSetBuilder private constructor(
    var foregroundColor: TileColor = StyleSet.defaultStyle().foregroundColor,
    var backgroundColor: TileColor = StyleSet.defaultStyle().backgroundColor,
    var modifiers: Set<Modifier> = StyleSet.defaultStyle().modifiers
) : Builder<StyleSet> {

    fun withForegroundColor(foregroundColor: TileColor) = also {
        this.foregroundColor = foregroundColor
    }

    fun withBackgroundColor(backgroundColor: TileColor) = also {
        this.backgroundColor = backgroundColor
    }

    fun withModifiers(modifiers: Set<Modifier>) = also {
        this.modifiers = modifiers.toSet()
    }

    fun withModifiers(vararg modifiers: Modifier) = also {
        this.modifiers = modifiers.toSet()
    }

    override fun build(): StyleSet = DefaultStyleSet(
        foregroundColor = foregroundColor,
        backgroundColor = backgroundColor,
        modifiers = modifiers.toMutableSet()
    )

    override fun createCopy() = StyleSetBuilder(
        foregroundColor = foregroundColor,
        backgroundColor = backgroundColor,
        modifiers = modifiers
    )

    companion object {

        /**
         * Creates a new [StyleSetBuilder] for creating [org.hexworks.zircon.api.graphics.StyleSet]s.
         */
        @JvmStatic
        fun newBuilder() = StyleSetBuilder()
    }
}
