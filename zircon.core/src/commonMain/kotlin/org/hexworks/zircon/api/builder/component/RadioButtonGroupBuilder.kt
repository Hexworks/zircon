package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.RadioButton
import org.hexworks.zircon.api.component.RadioButtonGroup
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.component.impl.DefaultRadioButtonGroup
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.dsl.ZirconDsl

@ZirconDsl
class RadioButtonGroupBuilder : Builder<RadioButtonGroup> {

    var isDisabled: Boolean = false
    var isHidden: Boolean = false
    var theme: ColorTheme = RuntimeConfig.config.defaultColorTheme
    var tileset: TilesetResource = RuntimeConfig.config.defaultTileset
    var radioButtons: List<RadioButton> = listOf()

    override fun build(): RadioButtonGroup = DefaultRadioButtonGroup(
        initialIsDisabled = isDisabled,
        initialIsHidden = isHidden,
        initialTheme = theme,
        initialTileset = tileset
    ).apply {
        addComponents(*radioButtons.toTypedArray())
    }
}

/**
 * Creates a new [RadioButtonGroup] using the component builder DSL and returns it.
 */
fun radioButtonGroup(init: RadioButtonGroupBuilder.() -> Unit): RadioButtonGroup =
    RadioButtonGroupBuilder().apply(init).build()
