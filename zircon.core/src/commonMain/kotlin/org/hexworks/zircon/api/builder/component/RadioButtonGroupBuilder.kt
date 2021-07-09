package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.RadioButton
import org.hexworks.zircon.api.component.RadioButtonGroup
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.component.impl.DefaultRadioButtonGroup
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.dsl.ZirconDsl
import kotlin.jvm.JvmStatic

@Suppress("UNCHECKED_CAST")
@ZirconDsl
class RadioButtonGroupBuilder : Builder<RadioButtonGroup> {

    var isDisabled: Boolean = false
    var isHidden: Boolean = false
    var theme: ColorTheme = RuntimeConfig.config.defaultColorTheme
    var tileset: TilesetResource = RuntimeConfig.config.defaultTileset
    var radioButtons: List<RadioButton> = listOf()

    fun withIsDisabled(isDisabled: Boolean) = also {
        this.isDisabled = isDisabled
    }

    fun withIsHidden(isHidden: Boolean) = also {
        this.isHidden = isHidden
    }

    fun withTheme(theme: ColorTheme) = also {
        this.theme = theme
    }

    fun withTileset(tileset: TilesetResource) = also {
        this.tileset = tileset
    }

    fun withRadioButtons(vararg radioButtons: RadioButton) = also {
        this.radioButtons = radioButtons.toList()
    }

    fun withAddedRadioButtons(vararg radioButtons: RadioButton) = also {
        this.radioButtons = this.radioButtons + radioButtons.toList()
    }

    override fun build(): RadioButtonGroup = DefaultRadioButtonGroup(
        initialIsDisabled = isDisabled,
        initialIsHidden = isHidden,
        initialTheme = theme,
        initialTileset = tileset
    ).apply {
        addComponents(*radioButtons.toTypedArray())
    }

    override fun createCopy() = newBuilder()
        .withIsDisabled(isDisabled)
        .withIsHidden(isHidden)
        .withTheme(theme)
        .withTileset(tileset)
        .withRadioButtons(*radioButtons.toTypedArray())


    companion object {

        @JvmStatic
        fun newBuilder() = RadioButtonGroupBuilder()
    }
}
