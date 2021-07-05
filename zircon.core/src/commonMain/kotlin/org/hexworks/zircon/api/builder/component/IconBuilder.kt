package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.Icon
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.internal.component.impl.DefaultIcon
import org.hexworks.zircon.internal.component.renderer.DefaultIconRenderer
import org.hexworks.zircon.internal.dsl.ZirconDsl
import kotlin.jvm.JvmStatic

@Suppress("UNCHECKED_CAST")
@ZirconDsl
class IconBuilder : BaseComponentBuilder<Icon, IconBuilder>(DefaultIconRenderer()) {

    var iconTile: Tile = Tile.empty()

    fun withIcon(icon: Tile) = also {
        this.iconTile = icon
    }

    override fun build(): Icon {
        return DefaultIcon(
                componentMetadata = createMetadata(),
                renderingStrategy = createRenderingStrategy(),
                initialIcon = iconTile
        )
    }

    override fun createCopy() = newBuilder().withProps(props.copy()).withIcon(iconTile)

    companion object {

        @JvmStatic
        fun newBuilder() = IconBuilder()
    }
}
