package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Group
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.component.impl.DefaultGroup
import org.hexworks.zircon.internal.config.RuntimeConfig
import kotlin.jvm.JvmStatic

data class GroupBuilder(
        private var colorTheme: ColorTheme = RuntimeConfig.config.defaultColorTheme,
        private var isHidden: Boolean = false,
        private var tileset: TilesetResource = RuntimeConfig.config.defaultTileset)
    : Builder<Group> {

    fun withColorTheme(colorTheme: ColorTheme) = also {
        this.colorTheme = colorTheme
    }

    fun withIsHidden(isHidden: Boolean) = also {
        this.isHidden = isHidden
    }

    fun withTileset(tileset: TilesetResource) = also {
        this.tileset = tileset
    }

    override fun build(): Group = DefaultGroup(
            initialTheme = colorTheme,
            initialIsHidden = isHidden,
            initialTileset = tileset)

    override fun createCopy() = copy()

    companion object {

        @JvmStatic
        fun newBuilder() = GroupBuilder()
    }
}