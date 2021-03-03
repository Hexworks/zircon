package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.Group
import org.hexworks.zircon.internal.dsl.ZirconDsl
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.component.impl.DefaultGroup
import org.hexworks.zircon.internal.config.RuntimeConfig
import kotlin.jvm.JvmStatic

@ZirconDsl
class GroupBuilder<T : Component>(
    private var isDisabled: Boolean = false,
    private var isHidden: Boolean = false,
    private var theme: ColorTheme = RuntimeConfig.config.defaultColorTheme,
    private var tileset: TilesetResource = RuntimeConfig.config.defaultTileset
) : Builder<Group<T>> {

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

    override fun build(): Group<T> = DefaultGroup(
        initialIsDisabled = isDisabled,
        initialIsHidden = isHidden,
        initialTheme = theme,
        initialTileset = tileset
    )

    override fun createCopy() = GroupBuilder<T>(
        isDisabled = isDisabled,
        isHidden = isHidden,
        tileset = tileset,
        theme = theme
    )

    companion object {

        @JvmStatic
        fun <T : Component> newBuilder() = GroupBuilder<T>()
    }
}
