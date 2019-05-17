package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.component.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.data.CommonComponentProperties
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.component.impl.DefaultVBox
import org.hexworks.zircon.internal.component.renderer.DefaultVBoxRenderer
import org.hexworks.zircon.internal.component.renderer.DefaultPanelRenderer
import kotlin.jvm.JvmStatic

@Suppress("UNCHECKED_CAST")
data class VBoxBuilder(
        private var spacing: Int = 0,
        private val commonComponentProperties: CommonComponentProperties<VBox> = CommonComponentProperties(
                componentRenderer = DefaultVBoxRenderer()))
    : BaseComponentBuilder<VBox, VBoxBuilder>(commonComponentProperties) {

    fun withSpacing(spacing: Int) = also {
        require(spacing >= 0) {
            "Can't use a negative spacing"
        }
        this.spacing = spacing
    }

    override fun build(): VBox {
        require(size != Size.unknown()) {
            "You must set a size for a VBox!"
        }
        fillMissingValues()
        return DefaultVBox(
                componentMetadata = ComponentMetadata(
                        size = size,
                        position = fixPosition(size),
                        componentStyleSet = commonComponentProperties.componentStyleSet,
                        tileset = tileset),
                initialTitle = title,
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = decorationRenderers,
                        componentRenderer = commonComponentProperties.componentRenderer as ComponentRenderer<VBox>),
                spacing = spacing)
    }

    override fun createCopy() = copy(commonComponentProperties = commonComponentProperties.copy())

    companion object {

        @JvmStatic
        fun newBuilder() = VBoxBuilder()
    }
}
