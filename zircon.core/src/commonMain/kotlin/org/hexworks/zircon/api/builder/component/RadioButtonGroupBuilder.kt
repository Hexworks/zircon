package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.RadioButton
import org.hexworks.zircon.api.component.RadioButtonGroup
import org.hexworks.zircon.api.dsl.ZirconDsl
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.component.impl.DefaultRadioButtonGroup
import org.hexworks.zircon.internal.config.RuntimeConfig

@ZirconDsl
public class RadioButtonGroupBuilder : Builder<RadioButtonGroup> {

    public var isDisabled: Boolean = false
    public var isHidden: Boolean = false
    public var theme: ColorTheme = RuntimeConfig.config.defaultColorTheme
    public var tileset: TilesetResource = RuntimeConfig.config.defaultTileset
    public var radioButtons: List<RadioButton> = listOf()

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
public fun radioButtonGroup(init: RadioButtonGroupBuilder.() -> Unit): RadioButtonGroup =
    RadioButtonGroupBuilder().apply(init).build()
