package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.BaseComponentBuilder
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.TextBox
import org.hexworks.zircon.api.component.data.CommonComponentProperties
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.component.impl.DefaultTextBox
import org.hexworks.zircon.internal.component.renderer.DefaultTextBoxRenderer
import org.hexworks.zircon.platform.factory.ThreadSafeQueueFactory
import kotlin.jvm.JvmOverloads

data class TextBoxBuilder(
        private var text: String = "",
        private var contentWidth: Int = 0,
        private var nextPosition: Position = Position.defaultPosition(),
        private var currentSize: Size = Size.zero(),
        private val components: MutableList<Component> = mutableListOf(),
        private val commonComponentProperties: CommonComponentProperties = CommonComponentProperties())
    : BaseComponentBuilder<TextBox, TextBoxBuilder>(commonComponentProperties) {

    private val inlineElements = ThreadSafeQueueFactory.create<Component>()

    fun contentWidth(width: Int) = also {
        this.contentWidth = width
        currentSize = currentSize.withWidth(width)
    }

    override fun withSize(size: Size): TextBoxBuilder {
        throw UnsupportedOperationException("You can't set a size for a TextBox by hand. Try setting width instead.")
    }

    @JvmOverloads
    fun header(text: String, withNewLine: Boolean = true) = also {
        val size = Size.create(contentWidth, text.length.div(contentWidth) + 1)
        components.add(HeaderBuilder.newBuilder()
                .withSize(size)
                .text(text)
                .withPosition(nextPosition)
                .build())
        updateSizeAndPosition(size.height())
        if (withNewLine) {
            newLine()
        }
    }

    @JvmOverloads
    fun paragraph(paragraph: String, withNewLine: Boolean = true, withTypingEffect: Boolean = false) = also {
        val size = Size.create(contentWidth, paragraph.length.div(contentWidth) + 1)
        components.add(ParagraphBuilder.newBuilder()
                .withSize(size)
                .text(paragraph)
                .withTypingEffect(withTypingEffect)
                .withPosition(nextPosition)
                .build())
        updateSizeAndPosition(size.height())
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
        updateSizeAndPosition(size.height())
    }

    fun inlineText(text: String) = also {
        val currentInlineLength = currentInlineLength()
        require(currentInlineLength + text.length < contentWidth) {
            "The length of elements in the current line can't be bigger than '$contentWidth'."
        }
        inlineElements.add(LabelBuilder.newBuilder()
                .text(text)
                .withPosition(Position.create(currentInlineLength, 0))
                .build())
    }

    fun inlineComponent(component: Component) = also {
        val currentInlineLength = currentInlineLength()
        require(currentInlineLength + component.width < contentWidth) {
            "The length of elements in the current line can't be bigger than '$contentWidth'."
        }
        require(component.height == 1) {
            "An inline Component can only have a height of 1."
        }
        component.moveRightBy(currentInlineLength)
        inlineElements.add(component)
    }

    fun commitInlineElements() = also {
        inlineElements.drainAll().forEach { component ->
            component.moveDownBy(nextPosition.y)
            components.add(component)
        }
        updateSizeAndPosition(1)
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
                .map { it.occupiedSize }
                .fold(Size.zero(), Size::plus)
        return DefaultTextBox(
                componentMetadata = ComponentMetadata(
                        size = currentSize + decorationSize,
                        position = position,
                        componentStyleSet = componentStyleSet,
                        tileset = tileset()),
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = decorationRenderers(),
                        componentRenderer = DefaultTextBoxRenderer())).also { textBox ->
            components.forEach {
                textBox.addComponent(it)
            }
        }
    }

    private fun updateSizeAndPosition(lastComponentHeight: Int) {
        currentSize = currentSize.withRelativeYLength(lastComponentHeight)
        nextPosition = nextPosition.withRelativeY(lastComponentHeight)
    }

    private fun currentInlineLength(): Int {
        return inlineElements.asSequence()
                .map { it.size.width() }
                .fold(0, Int::plus)
    }

    override fun createCopy() = copy()

    companion object {

        fun newBuilder() = TextBoxBuilder()
    }
}
