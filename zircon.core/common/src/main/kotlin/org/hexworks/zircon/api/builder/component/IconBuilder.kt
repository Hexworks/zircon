package org.hexworks.zircon.api.builder.component

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.component.Icon
import org.hexworks.zircon.api.component.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.data.CommonComponentProperties
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.internal.component.impl.DefaultIcon
import org.hexworks.zircon.internal.component.renderer.DefaultIconRenderer
import org.hexworks.zircon.internal.config.RuntimeConfig
import kotlin.jvm.JvmStatic

@Suppress("UNCHECKED_CAST")
data class IconBuilder(
        override val props: CommonComponentProperties<Icon> = CommonComponentProperties(
                componentRenderer = DefaultIconRenderer(),
                tileset = RuntimeConfig.config.defaultGraphicTileset))
    : BaseComponentBuilder<Icon, IconBuilder>() {

    private var icon = Maybe.empty<Tile>()

    override fun withSize(size: Size) = also {
        throw UnsupportedOperationException("Can't set the Size of a Modal by hand, use withParentSize instead.")
    }

    fun withIcon(icon: Tile) = also {
        this.icon = Maybe.of(icon)
    }

    override fun build(): Icon {
        require(icon.isPresent) {
            "Can't build an Icon without an icon tile"
        }
        return DefaultIcon(
                componentMetadata = ComponentMetadata(
                        size = size,
                        relativePosition = position,
                        componentStyleSet = componentStyleSet,
                        tileset = tileset),
                initialIcon = icon.get(),
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = decorationRenderers,
                        componentRenderer = componentRenderer as ComponentRenderer<Icon>))
    }

    override fun createCopy() = copy(props = props.copy())

    companion object {

        @JvmStatic
        fun newBuilder() = IconBuilder()
    }
}
