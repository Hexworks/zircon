package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.Panel
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.data.CommonComponentProperties
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.impl.DefaultPanel
import org.hexworks.zircon.internal.component.renderer.DefaultPanelRenderer
import kotlin.jvm.JvmStatic

@Suppress("UNCHECKED_CAST")
data class PanelBuilder(
        override val props: CommonComponentProperties<Panel> = CommonComponentProperties(
                componentRenderer = DefaultPanelRenderer()))
    : BaseComponentBuilder<Panel, PanelBuilder>() {

    override fun build(): Panel {
        return DefaultPanel(
                componentMetadata = ComponentMetadata(
                        size = size,
                        relativePosition = position,
                        componentStyleSet = componentStyleSet,
                        tileset = tileset),
                initialTitle = title,
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = decorationRenderers,
                        componentRenderer = componentRenderer as ComponentRenderer<Panel>))
    }

    override fun createCopy() = copy(props = props.copy())

    companion object {

        @JvmStatic
        fun newBuilder() = PanelBuilder()
    }
}
