package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.data.CommonComponentProperties
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
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
                        relativePosition = position,
                        componentStyleSet = componentStyleSet,
                        tileset = tileset),
                initialTitle = title,
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = decorationRenderers,
                        componentRenderer = componentRenderer as ComponentRenderer<VBox>),
                spacing = spacing).also {
            if(colorTheme !== ColorThemes.default()) {
                it.theme = colorTheme
            }
        }
    }

    override fun createCopy() = copy(props = props.copy())

    companion object {

        @JvmStatic
        fun newBuilder() = VBoxBuilder()
    }
}
