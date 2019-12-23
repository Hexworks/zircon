package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.Header
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.internal.component.impl.DefaultHeader
import org.hexworks.zircon.internal.component.renderer.DefaultComponentRenderingStrategy
import org.hexworks.zircon.internal.component.renderer.DefaultHeaderRenderer
import kotlin.jvm.JvmStatic
import kotlin.math.max

@Suppress("UNCHECKED_CAST")
class HeaderBuilder(
        private var text: String = "")
    : BaseComponentBuilder<Header, HeaderBuilder>(DefaultHeaderRenderer()) {

    fun withText(text: String) = also {
        this.text = text
        contentSize = contentSize
                .withWidth(max(text.length, contentSize.width))
    }

    override fun build(): Header {
        return DefaultHeader(
                componentMetadata = ComponentMetadata(
                        size = size,
                        relativePosition = position,
                        componentStyleSet = componentStyleSet,
                        tileset = tileset),
                initialText = text,
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = decorationRenderers,
                        componentRenderer = componentRenderer as ComponentRenderer<Header>)).apply {
            colorTheme.map {
                theme = it
            }
        }
    }

    override fun createCopy() = newBuilder().withProps(props.copy())
            .withText(text)

    companion object {

        @JvmStatic
        fun newBuilder() = HeaderBuilder()
    }
}
