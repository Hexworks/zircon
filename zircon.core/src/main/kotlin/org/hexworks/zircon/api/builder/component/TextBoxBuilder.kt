package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.BaseComponentBuilder
import org.hexworks.zircon.api.component.CommonComponentProperties
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.TextBox
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.component.impl.DefaultTextBox
import org.hexworks.zircon.internal.component.renderer.DefaultTextBoxRenderer

data class TextBoxBuilder(
        private var text: String = "",
        private var contentWidth: Int = 0,
        private var nextPosition: Position = Position.defaultPosition(),
        private var currentSize: Size = Size.one(),
        private val components: MutableList<Component> = mutableListOf(),
        private val commonComponentProperties: CommonComponentProperties = CommonComponentProperties())
    : BaseComponentBuilder<TextBox, TextBoxBuilder>(commonComponentProperties) {

    fun contentWidth(width: Int) = also {
        this.contentWidth = width
        currentSize = currentSize.withWidth(width)
    }

    override fun withSize(size: Size): TextBoxBuilder {
        throw UnsupportedOperationException("You can't set a size for a TextBox by hand. Try setting width instead.")
    }

    fun header(text: String) = also {
        header(text, true)
    }

    fun header(text: String, withNewLine: Boolean = true) = also {
        val size = Size.create(contentWidth, text.length.div(contentWidth) + 1)
        components.add(HeaderBuilder.newBuilder()
                .withSize(size)
                .text(text)
                .withPosition(nextPosition)
                .build())
        currentSize = currentSize.withRelativeYLength(size.yLength)
        nextPosition = nextPosition.withRelativeY(size.yLength)
        if (withNewLine) {
            newLine()
        }
    }

    fun paragraph(paragraph: String) = also {
        paragraph(paragraph, true)
    }

    fun paragraph(paragraph: String, withNewLine: Boolean = true) = also {
        val size = Size.create(contentWidth, paragraph.length.div(contentWidth) + 1)
        components.add(ParagraphBuilder.newBuilder()
                .withSize(size)
                .text(paragraph)
                .withPosition(nextPosition)
                .build())
        currentSize = currentSize.withRelativeYLength(size.yLength)
        nextPosition = nextPosition.withRelativeY(size.yLength)
        if (withNewLine) {
            newLine()
        }
    }

    fun listItem(item: String) = also {
        val size = Size.create(contentWidth, item.length.div(contentWidth) + 1)
        components.add(ListItemBuilder.newBuilder()
                .withSize(size)
                .text(item)
                .withPosition(nextPosition)
                .build())
        currentSize = currentSize.withRelativeYLength(size.yLength)
        nextPosition = nextPosition.withRelativeY(size.yLength)
    }

    fun newLine() = also {
        nextPosition = nextPosition.withRelativeY(1)
        currentSize = currentSize.withRelativeYLength(1)
    }

    override fun build(): TextBox {
        require(currentSize != Size.unknown()) {
            "You must set a size for a TextBox!"
        }
        fillMissingValues()
        val decorationSize = decorationRenderers().asSequence()
                .map { it.occupiedSize() }
                .fold(Size.zero(), Size::plus)
        return DefaultTextBox(
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = decorationRenderers(),
                        componentRenderer = DefaultTextBoxRenderer()),
                size = currentSize + decorationSize,
                position = position,
                componentStyleSet = componentStyleSet(),
                tileset = tileset()).also { textBox ->
            components.forEach {
                textBox.addComponent(it)
            }
        }
    }

    override fun createCopy() = copy()

    companion object {

        fun newBuilder() = TextBoxBuilder()
    }
}
