package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.HBox
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.internal.component.impl.DefaultHBox
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.renderer.DefaultHBoxRenderer
import kotlin.jvm.JvmStatic

@Suppress("UNCHECKED_CAST")
class HBoxBuilder(
    private var spacing: Int = 0
) : BaseComponentBuilder<HBox, HBoxBuilder>(DefaultHBoxRenderer()) {

    fun withSpacing(spacing: Int) = also {
        require(spacing >= 0) {
            "Can't use a negative spacing"
        }
        this.spacing = spacing
    }

    override fun build(): HBox {
        return DefaultHBox(
            componentMetadata = generateMetadata(),
            initialTitle = title,
            renderingStrategy = DefaultComponentRenderingStrategy(
                decorationRenderers = decorationRenderers,
                componentRenderer = componentRenderer as ComponentRenderer<HBox>
            ),
            spacing = spacing
        )
    }

    override fun createCopy() = newBuilder().withProps(props.copy())
        .withSpacing(spacing)

    companion object {

        @JvmStatic
        fun newBuilder() = HBoxBuilder()
    }
}
