package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.HBox
import org.hexworks.zircon.api.component.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.data.CommonComponentProperties
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.impl.DefaultHBox
import org.hexworks.zircon.internal.component.renderer.DefaultHBoxRenderer
import kotlin.jvm.JvmStatic

@Suppress("UNCHECKED_CAST")
data class HBoxBuilder(
        private var spacing: Int = 0,
        override val props: CommonComponentProperties<HBox> = CommonComponentProperties(
                componentRenderer = DefaultHBoxRenderer()))
    : BaseComponentBuilder<HBox, HBoxBuilder>() {

    fun withSpacing(spacing: Int) = also {
        require(spacing >= 0) {
            "Can't use a negative spacing"
        }
        this.spacing = spacing
    }

    override fun build(): HBox {
        return DefaultHBox(
                componentMetadata = ComponentMetadata(
                        size = size,
                        position = position,
                        componentStyleSet = props.componentStyleSet,
                        tileset = tileset),
                initialTitle = title,
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = decorationRenderers,
                        componentRenderer = componentRenderer as ComponentRenderer<HBox>),
                spacing = spacing)
    }

    override fun createCopy() = copy(props = props.copy())

    companion object {

        @JvmStatic
        fun newBuilder() = HBoxBuilder()
    }
}
