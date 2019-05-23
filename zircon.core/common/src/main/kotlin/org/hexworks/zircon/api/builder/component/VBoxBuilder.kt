package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.component.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.data.CommonComponentProperties
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.impl.DefaultVBox
import org.hexworks.zircon.internal.component.renderer.DefaultVBoxRenderer
import kotlin.jvm.JvmStatic

@Suppress("UNCHECKED_CAST")
data class VBoxBuilder(
        private var spacing: Int = 0,
        override val props: CommonComponentProperties<VBox> = CommonComponentProperties(
                componentRenderer = DefaultVBoxRenderer()))
    : BaseComponentBuilder<VBox, VBoxBuilder>() {

    fun withSpacing(spacing: Int) = also {
        require(spacing >= 0) {
            "Can't use a negative spacing"
        }
        this.spacing = spacing
    }

    override fun build(): VBox {
        return DefaultVBox(
                componentMetadata = ComponentMetadata(
                        size = size,
                        position = position,
                        componentStyleSet = props.componentStyleSet,
                        tileset = tileset),
                initialTitle = title,
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = decorationRenderers,
                        componentRenderer = props.componentRenderer as ComponentRenderer<VBox>),
                spacing = spacing)
    }

    override fun createCopy() = copy(props = props.copy())

    companion object {

        @JvmStatic
        fun newBuilder() = VBoxBuilder()
    }
}
