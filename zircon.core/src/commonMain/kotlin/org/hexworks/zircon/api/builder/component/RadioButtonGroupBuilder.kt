package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.RadioButtonGroup
import org.hexworks.zircon.internal.dsl.ZirconDsl
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.component.impl.DefaultRadioButtonGroup
import org.hexworks.zircon.internal.config.RuntimeConfig
import kotlin.jvm.JvmStatic

@Suppress("UNCHECKED_CAST")
@ZirconDsl
class RadioButtonGroupBuilder(
    private var isDisabled: Boolean = false,
    private var isHidden: Boolean = false,
    private var theme: ColorTheme = RuntimeConfig.config.defaultColorTheme,
    private var tileset: TilesetResource = RuntimeConfig.config.defaultTileset
) : Builder<RadioButtonGroup> {

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

    override fun build(): RadioButtonGroup = DefaultRadioButtonGroup(
        initialIsDisabled = isDisabled,
        initialIsHidden = isHidden,
        initialTheme = theme,
        initialTileset = tileset
    )

    override fun createCopy() = RadioButtonGroupBuilder(
        isDisabled = isDisabled,
        isHidden = isHidden,
        theme = theme,
        tileset = tileset
    )

    companion object {

        @JvmStatic
        fun newBuilder() = RadioButtonGroupBuilder()
    }
}
