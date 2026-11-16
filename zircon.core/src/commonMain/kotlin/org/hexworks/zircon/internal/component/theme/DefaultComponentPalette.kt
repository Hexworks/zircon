package org.hexworks.zircon.internal.component.theme

import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.component.theme.ComponentPalette
import org.hexworks.zircon.api.component.theme.PaletteKey
import org.hexworks.zircon.api.component.theme.StyleOverrideKey
import org.hexworks.zircon.api.graphics.StyleSet

/**
 * A palette as data: every key present, compared by value so two palettes built the same way
 * are equal (ZirconDefaults is compared that way).
 */
internal data class DefaultComponentPalette(
    override val name: String,
    override val isDark: Boolean,
    private val colors: Map<PaletteKey, Color>,
    override val overrides: Map<StyleOverrideKey, StyleSet> = emptyMap(),
) : ComponentPalette {

    init {
        val missing = PaletteKey.entries.filter { it !in colors }
        require(missing.isEmpty()) { "Palette '$name' lacks colors for $missing." }
    }

    override fun get(key: PaletteKey): Color = colors.getValue(key)

    override fun withPrimary(color: Color, onColor: Color): ComponentPalette =
        copy(colors = colors + (PaletteKey.PRIMARY to color) + (PaletteKey.ON_PRIMARY to onColor))

    override fun toString(): String = "ComponentPalette($name)"
}
