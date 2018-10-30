package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.Panel
import org.hexworks.zircon.api.component.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.data.CommonComponentProperties
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.component.impl.DefaultPanel
import org.hexworks.zircon.internal.component.renderer.DefaultPanelRenderer
import kotlin.jvm.JvmStatic

data class PanelBuilder(
        private val commonComponentProperties: CommonComponentProperties<Panel> = CommonComponentProperties())
    : BaseComponentBuilder<Panel, PanelBuilder>(commonComponentProperties) {

    override fun build(): Panel {
        require(size != Size.unknown()) {
            "You must set a size for a Panel!"
        }
        fillMissingValues()
        return DefaultPanel(
                componentMetadata = ComponentMetadata(
                        size = size,
                        position = position,
                        componentStyleSet = commonComponentProperties.componentStyleSet,
                        tileset = tileset),
                title = title,
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = decorationRenderers,
                        componentRenderer = DefaultPanelRenderer()))
    }

    override fun createCopy() = copy(commonComponentProperties = commonComponentProperties.copy())

    companion object {

        @JvmStatic
        fun newBuilder() = PanelBuilder()
    }
}
