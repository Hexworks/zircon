package org.hexworks.zircon.api.builder.component

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.component.Icon
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.internal.component.impl.DefaultIcon
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.renderer.DefaultIconRenderer
import org.hexworks.zircon.internal.config.RuntimeConfig
import kotlin.jvm.JvmStatic

@Suppress("UNCHECKED_CAST")
class IconBuilder : BaseComponentBuilder<Icon, IconBuilder>(DefaultIconRenderer()) {

    private var icon = Maybe.empty<Tile>()

    init {
        withTileset(RuntimeConfig.config.defaultGraphicalTileset)
    }

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
                        componentRenderer = componentRenderer as ComponentRenderer<Icon>)).apply {
            colorTheme.map {
                theme = it
            }
        }
    }

    override fun createCopy() = newBuilder().withProps(props.copy()).apply {
        icon.map {
            withIcon(it)
        }
    }

    companion object {

        @JvmStatic
        fun newBuilder() = IconBuilder()
    }
}
