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
        private val commonComponentProperties: CommonComponentProperties<Icon> = CommonComponentProperties(
                componentRenderer = DefaultIconRenderer(),
                tileset = RuntimeConfig.config.defaultGraphicTileset))
    : BaseComponentBuilder<Icon, IconBuilder>(commonComponentProperties) {

    private var icon = Maybe.empty<Tile>()

    fun withIcon(icon: Tile) = also {
        this.icon = Maybe.of(icon)
    }

    override fun build(): Icon {
        require(icon.isPresent) {
            "Can't build an Icon without an icon tile"
        }
        fillMissingValues()
        val finalSize = if (size.isUnknown()) {
            decorationRenderers.asSequence()
                    .map { it.occupiedSize }
                    .fold(Size.one(), Size::plus)
        } else {
            size
        }
        return DefaultIcon(
                componentMetadata = ComponentMetadata(
                        size = finalSize,
                        position = fixPosition(finalSize),
                        componentStyleSet = componentStyleSet,
                        tileset = tileset),
                initialIcon = icon.get(),
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = decorationRenderers,
                        componentRenderer = commonComponentProperties.componentRenderer as ComponentRenderer<Icon>))
    }

    override fun createCopy() = copy()

    companion object {

        @JvmStatic
        fun newBuilder() = IconBuilder()
    }
}
