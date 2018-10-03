package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.BaseComponentBuilder
import org.hexworks.zircon.api.component.CommonComponentProperties
import org.hexworks.zircon.api.component.Header
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.component.impl.DefaultHeader
import org.hexworks.zircon.internal.component.renderer.DefaultHeaderRenderer

data class HeaderBuilder(
        private var text: String = "",
        private val commonComponentProperties: CommonComponentProperties = CommonComponentProperties())
    : BaseComponentBuilder<Header, HeaderBuilder>(commonComponentProperties) {

    fun text(text: String) = also {
        this.text = text
    }

    override fun build(): Header {
        require(text.isNotBlank()) {
            "A Header can't be blank!"
        }
        fillMissingValues()
        val finalSize = if (size.isUnknown()) {
            decorationRenderers().map { it.occupiedSize }
                    .fold(Size.create(text.length, 1), Size::plus)
        } else {
            size
        }
        return DefaultHeader(
                text = text,
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = decorationRenderers(),
                        componentRenderer = DefaultHeaderRenderer()),
                initialSize = finalSize,
                position = position,
                componentStyleSet = componentStyleSet,
                initialTileset = tileset())
    }

    override fun createCopy() = copy()

    companion object {

        fun newBuilder() = HeaderBuilder()
    }
}
