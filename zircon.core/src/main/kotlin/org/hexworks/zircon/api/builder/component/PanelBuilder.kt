package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.BaseComponentBuilder
import org.hexworks.zircon.api.component.CommonComponentProperties
import org.hexworks.zircon.api.component.Panel
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.component.impl.DefaultPanel
import org.hexworks.zircon.internal.component.renderer.DefaultPanelRenderer

data class PanelBuilder(
        private val commonComponentProperties: CommonComponentProperties = CommonComponentProperties())
    : BaseComponentBuilder<Panel, PanelBuilder>(commonComponentProperties) {

    override fun build(): Panel {
        require(size() != Size.unknown()) {
            "You must set a size for a Panel!"
        }
        fillMissingValues()
        return DefaultPanel(
                title = title().orElseGet { "" },
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = decorationRenderers(),
                        componentRenderer = DefaultPanelRenderer()),
                size = size(),
                position = position(),
                componentStyleSet = componentStyleSet(),
                tileset = tileset())
    }

    override fun createCopy() = copy(commonComponentProperties = commonComponentProperties.copy())

    companion object {

        fun newBuilder() = PanelBuilder()
    }
}
