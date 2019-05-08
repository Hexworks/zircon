package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.HBox
import org.hexworks.zircon.api.component.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.data.CommonComponentProperties
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.component.impl.DefaultHBox
import org.hexworks.zircon.internal.component.renderer.DefaultHBoxRenderer
import org.hexworks.zircon.internal.component.renderer.DefaultPanelRenderer
import kotlin.jvm.JvmStatic

@Suppress("UNCHECKED_CAST")
data class HBoxBuilder(
        private var spacing: Int = 0,
        private val commonComponentProperties: CommonComponentProperties<HBox> = CommonComponentProperties(
                componentRenderer = DefaultHBoxRenderer()))
    : BaseComponentBuilder<HBox, HBoxBuilder>(commonComponentProperties) {

    fun withSpacing(spacing: Int) = also {
        require(spacing >= 0) {
            "Can't use a negative spacing"
        }
        this.spacing = spacing
    }

    override fun build(): HBox {
        require(size != Size.unknown()) {
            "You must set a size for a HBox!"
        }
        fillMissingValues()
        return DefaultHBox(
                componentMetadata = ComponentMetadata(
                        size = size,
                        position = fixPosition(size),
                        componentStyleSet = commonComponentProperties.componentStyleSet,
                        tileset = tileset),
                initialTitle = title,
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = decorationRenderers,
                        componentRenderer = commonComponentProperties.componentRenderer as ComponentRenderer<HBox>),
                spacing = spacing)
    }

    override fun createCopy() = copy(commonComponentProperties = commonComponentProperties.copy())

    companion object {

        @JvmStatic
        fun newBuilder() = HBoxBuilder()
    }
}
