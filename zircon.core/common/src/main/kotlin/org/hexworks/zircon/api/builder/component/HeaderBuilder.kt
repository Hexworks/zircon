package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.Header
import org.hexworks.zircon.api.component.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.data.CommonComponentProperties
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.component.impl.DefaultHeader
import org.hexworks.zircon.internal.component.renderer.DefaultHeaderRenderer
import kotlin.jvm.JvmStatic

@Suppress("UNCHECKED_CAST")
data class HeaderBuilder(
        private var text: String = "",
        private val commonComponentProperties: CommonComponentProperties<Header> = CommonComponentProperties(
                componentRenderer = DefaultHeaderRenderer()))
    : BaseComponentBuilder<Header, HeaderBuilder>(commonComponentProperties) {

    fun withText(text: String) = also {
        this.text = text
    }

    override fun build(): Header {
        require(text.isNotBlank()) {
            "A Header can't be blank!"
        }
        fillMissingValues()
        val finalSize = if (size.isUnknown()) {
            decorationRenderers.asSequence()
                    .map { it.occupiedSize }
                    .fold(Size.create(text.length, 1), Size::plus)
        } else {
            size
        }
        return DefaultHeader(
                componentMetadata = ComponentMetadata(
                        size = finalSize,
                        position = position,
                        componentStyleSet = componentStyleSet,
                        tileset = tileset),
                text = text,
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = decorationRenderers,
                        componentRenderer = commonComponentProperties.componentRenderer as ComponentRenderer<Header>))
    }

    override fun createCopy() = copy()

    companion object {

        @JvmStatic
        fun newBuilder() = HeaderBuilder()
    }
}
