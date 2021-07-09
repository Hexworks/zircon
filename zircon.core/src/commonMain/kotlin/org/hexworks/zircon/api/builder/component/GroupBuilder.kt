package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.Group
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.component.impl.DefaultGroup
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.dsl.ZirconDsl
import kotlin.jvm.JvmStatic

@ZirconDsl
class GroupBuilder<T : Component> : Builder<Group<T>> {

    var isDisabled: Boolean = false
    var isHidden: Boolean = false
    var theme: ColorTheme = RuntimeConfig.config.defaultColorTheme
    var tileset: TilesetResource = RuntimeConfig.config.defaultTileset
    var name: String = ""

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

    fun withName(name: String) = also {
        this.name = name
    }

    override fun build(): Group<T> = DefaultGroup(
        initialIsDisabled = isDisabled,
        initialIsHidden = isHidden,
        initialTheme = theme,
        initialTileset = tileset,
        name = name
    )

    override fun createCopy() = newBuilder<T>()
        .withIsDisabled(isDisabled)
        .withIsHidden(isHidden)
        .withTheme(theme)
        .withTileset(tileset)


    companion object {

        @JvmStatic
        fun <T : Component> newBuilder() = GroupBuilder<T>()
    }
}
