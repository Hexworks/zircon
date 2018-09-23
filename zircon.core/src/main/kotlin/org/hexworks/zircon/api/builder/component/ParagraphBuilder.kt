package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.BaseComponentBuilder
import org.hexworks.zircon.api.component.CommonComponentProperties
import org.hexworks.zircon.api.component.Paragraph
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.component.impl.DefaultParagraph
import org.hexworks.zircon.internal.component.renderer.DefaultParagraphRenderer

data class ParagraphBuilder(
        private var text: String = "",
        private val commonComponentProperties: CommonComponentProperties = CommonComponentProperties())
    : BaseComponentBuilder<Paragraph, ParagraphBuilder>(commonComponentProperties) {

    override fun title(title: String) = also { }

    fun text(text: String) = also {
        this.text = text
    }

    override fun build(): Paragraph {
        require(text.isNotBlank()) {
            "A Label can't be blank!"
        }
        fillMissingValues()
        // TODO: calculate size based on text size
        val size = if (size().isUnknown()) {
            decorationRenderers().asSequence()
                    .map { it.occupiedSize() }
                    .fold(Size.create(text.length, 1), Size::plus)
        } else {
            size()
        }
        return DefaultParagraph(
                text = text,
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = decorationRenderers(),
                        componentRenderer = DefaultParagraphRenderer()),
                size = size,
                position = position(),
                componentStyleSet = componentStyleSet(),
                tileset = tileset())
    }

    override fun createCopy() = copy(commonComponentProperties = commonComponentProperties.copy())

    companion object {

        fun newBuilder() = ParagraphBuilder()
    }
}
