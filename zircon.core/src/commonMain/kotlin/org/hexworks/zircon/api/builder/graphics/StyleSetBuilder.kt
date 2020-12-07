package org.hexworks.zircon.api.builder.graphics

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.Modifier
import kotlin.jvm.JvmStatic

/**
 * Builder used to create [StyleSet]s. Uses the default colors from
 * [TileColor]. Modifiers are empty by default.
 */
data class StyleSetBuilder(
    private var foregroundColor: TileColor = StyleSet.defaultStyle().foregroundColor,
    private var backgroundColor: TileColor = StyleSet.defaultStyle().backgroundColor,
    private var modifiers: Set<Modifier> = StyleSet.defaultStyle().modifiers
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

    override fun build(): StyleSet = StyleSet.create(
        foregroundColor = foregroundColor,
        backgroundColor = backgroundColor,
        modifiers = modifiers.toMutableSet()
    )

    override fun createCopy() = copy(
        modifiers = modifiers.toSet()
    )

    companion object {

        /**
         * Creates a new [StyleSetBuilder] for creating [org.hexworks.zircon.api.graphics.StyleSet]s.
         */
        @JvmStatic
        fun newBuilder() = StyleSetBuilder()
    }
}
