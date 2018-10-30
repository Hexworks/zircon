package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.TextBox
import org.hexworks.zircon.api.component.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.data.CommonComponentProperties
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.component.impl.DefaultTextBox
import org.hexworks.zircon.internal.component.renderer.DefaultTextBoxRenderer
import org.hexworks.zircon.platform.factory.ThreadSafeQueueFactory
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

@Suppress("UNCHECKED_CAST")
data class TextBoxBuilder(
        private var text: String = "",
        private var contentWidth: Int = 0,
        private var nextPosition: Position = Position.defaultPosition(),
        private var currentSize: Size = Size.zero(),
        private val components: MutableList<Component> = mutableListOf(),
        private val commonComponentProperties: CommonComponentProperties<TextBox> = CommonComponentProperties(
                componentRenderer = DefaultTextBoxRenderer()))
    : BaseComponentBuilder<TextBox, TextBoxBuilder>(commonComponentProperties) {

    private val inlineElements = ThreadSafeQueueFactory.create<Component>()

    fun withContentWidth(width: Int) = also {
        this.contentWidth = width
        currentSize = currentSize.withWidth(width)
    }

    override fun withSize(size: Size): TextBoxBuilder {
        throw UnsupportedOperationException("You can't set a size for a TextBox by hand. Try setting width instead.")
    }

    @JvmOverloads
    fun addHeader(text: String, withNewLine: Boolean = true) = also {
        val size = Size.create(contentWidth, text.length.div(contentWidth) + 1)
        components.add(HeaderBuilder.newBuilder()
                .withSize(size)
                .withText(text)
                // TODO: regression test tileset in all methods here
                .withTileset(tileset)
                .withPosition(nextPosition)
                .build())
        updateSizeAndPosition(size.height)
        if (withNewLine) {
            addNewLine()
        }
    }

    @JvmOverloads
    fun addParagraph(paragraph: String, withNewLine: Boolean = true, withTypingEffectSpeedInMs: Long = 0) = also {
        val size = Size.create(contentWidth, paragraph.length.div(contentWidth) + 1)
        components.add(ParagraphBuilder.newBuilder()
                .withSize(size)
                .withText(paragraph)
                .withTypingEffect(withTypingEffectSpeedInMs)
                .withTileset(tileset)
                .withPosition(nextPosition)
                .build())
        updateSizeAndPosition(size.height)
        if (withNewLine) {
            addNewLine()
        }
    }

    @JvmOverloads
    fun addParagraph(paragraphBuilder: ParagraphBuilder, withNewLine: Boolean = true) = also {
        val size = Size.create(contentWidth, paragraphBuilder.text.length.div(contentWidth) + 1)
        val paragraph = paragraphBuilder
                .withPosition(nextPosition)
                .withSize(size)
                .build()
        components.add(paragraph)
        updateSizeAndPosition(size.height)
        if (withNewLine) {
            addNewLine()
        }
    }

    fun addListItem(item: String) = also {
        val size = Size.create(contentWidth, item.length.div(contentWidth) + 1)
        components.add(ListItemBuilder.newBuilder()
                .withSize(size)
                .withText(item)
                .withPosition(nextPosition)
                .withTileset(tileset)
                .build())
        updateSizeAndPosition(size.height)
    }

    fun addInlineText(text: String) = also {
        val currentInlineLength = currentInlineLength()
        require(currentInlineLength + text.length < contentWidth) {
            "The length of elements in the current line can't be bigger than '$contentWidth'."
        }
        inlineElements.add(LabelBuilder.newBuilder()
                .withText(text)
                .withPosition(Position.create(currentInlineLength, 0))
                .withTileset(tileset)
                .build())
    }

    fun addInlineComponent(component: Component) = also {
        val currentInlineLength = currentInlineLength()
        require(currentInlineLength + component.width < contentWidth) {
            "The length of elements in the current line can't be bigger than '$contentWidth'."
        }
        require(component.height == 1) {
            "An inline Component can only have a height of 1."
        }
        require(tileset.size == component.currentTileset().size) {
            "Trying to add component with incompatible tileset size '${component.currentTileset().size}' to" +
                    "container with tileset size: '${tileset.size}'!"
        }
        component.moveRightBy(currentInlineLength)
        inlineElements.add(component)
    }

    fun addNewLine() = also {
        nextPosition = nextPosition.withRelativeY(1)
        currentSize = currentSize.withRelativeHeight(1)
    }

    fun commitInlineElements() = also {
        inlineElements.drainAll().forEach { component ->
            component.moveDownBy(nextPosition.y)
            components.add(component)
        }
        updateSizeAndPosition(1)
    }

    override fun build(): TextBox {
        require(currentSize != Size.unknown()) {
            "You must set a size for a TextBox!"
        }
        fillMissingValues()
        val decorationSize = decorationRenderers.asSequence()
                .map { it.occupiedSize }
                .fold(Size.zero(), Size::plus)
        return DefaultTextBox(
                componentMetadata = ComponentMetadata(
                        size = currentSize + decorationSize,
                        position = position,
                        componentStyleSet = componentStyleSet,
                        tileset = tileset),
                renderingStrategy = DefaultComponentRenderingStrategy(
                        decorationRenderers = decorationRenderers,
                        componentRenderer = commonComponentProperties.componentRenderer as ComponentRenderer<TextBox>)).also { textBox ->
            components.forEach {
                textBox.addComponent(it)
            }
        }
    }

    private fun updateSizeAndPosition(lastComponentHeight: Int) {
        currentSize = currentSize.withRelativeHeight(lastComponentHeight)
        nextPosition = nextPosition.withRelativeY(lastComponentHeight)
    }

    private fun currentInlineLength(): Int {
        return inlineElements.asSequence()
                .map { it.size.width }
                .fold(0, Int::plus)
    }

    override fun createCopy() = copy()

    companion object {

        @JvmStatic
        fun newBuilder() = TextBoxBuilder()
    }
}
