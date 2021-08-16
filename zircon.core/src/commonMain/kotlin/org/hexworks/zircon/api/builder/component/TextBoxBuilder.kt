package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.ComponentAlignments.positionalAlignment
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.Header
import org.hexworks.zircon.api.component.ListItem
import org.hexworks.zircon.api.component.Paragraph
import org.hexworks.zircon.api.component.TextBox
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.component.impl.DefaultTextBox
import org.hexworks.zircon.internal.component.renderer.DefaultTextBoxRenderer
import org.hexworks.zircon.internal.dsl.ZirconDsl
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic


@Suppress("UNCHECKED_CAST")
@ZirconDsl
class TextBoxBuilder private constructor(
    private val initialContentWidth: Int,
    initialPreferredContentSize: Size = Size.unknown().withWidth(initialContentWidth),
    private var nextPosition: Position = Position.defaultPosition(),
    private val components: MutableList<Component> = mutableListOf(),
    private val inlineElements: MutableList<Component> = mutableListOf()
) : BaseComponentBuilder<TextBox, TextBoxBuilder>(DefaultTextBoxRenderer()) {

    init {
        preferredContentSize = initialPreferredContentSize
    }

    override fun withSize(size: Size): TextBoxBuilder {
        withPreferredSize(size)
    }

    override fun withPreferredContentSize(size: Size): TextBoxBuilder {
        withPreferredSize(size)
    }

    override fun withPreferredSize(size: Size): Nothing {
        throw UnsupportedOperationException("You can't set a size for a TextBox by hand. Try setting width instead.")
    }

    @JvmOverloads
    fun addHeader(
        text: String,
        withNewLine: Boolean = true
    ) = also {
        addHeader(
            headerBuilder = HeaderBuilder.newBuilder()
                .withText(text)
                .withTileset(tileset),
            withNewLine = withNewLine
        )
    }

    @JvmOverloads
    fun addHeader(
        headerBuilder: HeaderBuilder,
        withNewLine: Boolean = true
    ) = also {
        val size = Size.create(contentWidth, headerBuilder.text.length.div(contentWidth) + 1)
        addHeader(
            header = headerBuilder
                .withAlignment(positionalAlignment(nextPosition))
                .withPreferredSize(size)
                .build(),
            withNewLine = withNewLine
        )
    }

    @JvmOverloads
    fun addHeader(
        header: Header,
        withNewLine: Boolean = true
    ): TextBoxBuilder = addBlockComponent(header, withNewLine)

    @JvmOverloads
    fun addParagraph(
        text: String,
        withNewLine: Boolean = true,
        withTypingEffectSpeedInMs: Long = 0
    ) = also {
        addParagraph(
            paragraphBuilder = ParagraphBuilder.newBuilder()
                .withText(text)
                .withTypingEffect(withTypingEffectSpeedInMs)
                .withTileset(tileset),
            withNewLine = withNewLine
        )
    }

    @JvmOverloads
    fun addParagraph(
        paragraphBuilder: ParagraphBuilder,
        withNewLine: Boolean = true
    ) = also {
        val size = Size.create(contentWidth, paragraphBuilder.text.length.div(contentWidth) + 1)
        addParagraph(
            paragraph = paragraphBuilder
                .withAlignment(positionalAlignment(nextPosition))
                .withPreferredSize(size)
                .build(),
            withNewLine = withNewLine
        )
    }

    @JvmOverloads
    fun addParagraph(
        paragraph: Paragraph,
        withNewLine: Boolean = true
    ): TextBoxBuilder = addBlockComponent(paragraph, withNewLine)

    @JvmOverloads
    fun addListItem(
        text: String,
        withNewLine: Boolean = true
    ) = also {
        addListItem(
            listItemBuilder = ListItemBuilder.newBuilder()
                .withText(text)
                .withTileset(tileset),
            withNewLine = withNewLine
        )
    }

    @JvmOverloads
    fun addListItem(
        listItemBuilder: ListItemBuilder,
        withNewLine: Boolean = true
    ) = also {
        val size = Size.create(contentWidth, listItemBuilder.text.length.div(contentWidth) + 1)
        addListItem(
            listItem = listItemBuilder
                .withAlignment(positionalAlignment(nextPosition))
                .withPreferredSize(size)
                .build(),
            withNewLine = withNewLine
        )
    }

    @JvmOverloads
    fun addListItem(
        listItem: ListItem,
        withNewLine: Boolean = true
    ): TextBoxBuilder = addBlockComponent(listItem, withNewLine)

    private fun addBlockComponent(
        component: Component,
        withNewLine: Boolean
    ): TextBoxBuilder = also {
        require(component.width <= contentWidth) {
            "Child width (${component.width} must be less than or equal to content width (${contentWidth})."
        }
        component.moveTo(nextPosition)
        components.add(component)
        updateSizeAndPosition(component.height)
        if (withNewLine) {
            addNewLine()
        }
    }


    fun addInlineText(text: String) = also {
        val currentInlineLength = currentInlineLength()
        require(currentInlineLength + text.length < contentWidth) {
            "The length of elements in the current line can't be bigger than '$contentWidth'."
        }
        inlineElements.add(
            LabelBuilder.newBuilder()
                .withText(text)
                .withAlignment(positionalAlignment(Position.create(currentInlineLength, 0)))
                .withTileset(tileset)
                .build()
        )
    }

    fun addInlineComponent(component: Component) = also {
        val currentInlineLength = currentInlineLength()
        require(currentInlineLength + component.width < contentWidth) {
            "The length of elements in the current line can't be bigger than '$contentWidth'."
        }
        require(component.height == 1) {
            "An inline Component can only have a height of 1."
        }
        require(tileset isCompatibleWith component.tileset) {
            "Trying to add component with incompatible tileset size '${component.tileset.size}' to" +
                    "container with tileset size: '${tileset.size}'!"
        }
        component.moveRightBy(currentInlineLength)
        inlineElements.add(component)
    }

    fun addNewLine() = also {
        nextPosition = nextPosition.withRelativeY(1)
        fixHeight(1)
    }

    fun commitInlineElements() = also {
        inlineElements.forEach { component ->
            component.moveDownBy(nextPosition.y)
            components.add(component)
        }
        inlineElements.clear()
        updateSizeAndPosition(1)
    }

    private fun updateSizeAndPosition(lastComponentHeight: Int) {
        fixHeight(lastComponentHeight)
        nextPosition = nextPosition.withRelativeY(lastComponentHeight)
    }

    private fun fixHeight(height: Int) {
        preferredContentSize = if (preferredContentSize.height == Size.unknown().height) {
            preferredContentSize.withHeight(height)
        } else {
            preferredContentSize.withRelativeHeight(height)
        }
    }

    private fun currentInlineLength(): Int {
        return inlineElements.asSequence()
            .map { it.size.width }
            .fold(0, Int::plus)
    }

    override fun build(): TextBox {
        return DefaultTextBox(
            componentMetadata = createMetadata(),
            renderingStrategy = createRenderingStrategy(),
        ).also { textBox ->
            components.forEach {
                textBox.addComponent(it)
            }
        }.attachListeners()
    }

    override fun createCopy() = TextBoxBuilder(
        initialContentWidth = contentWidth,
        nextPosition = nextPosition,
        components = components.toMutableList(),
        initialPreferredContentSize = preferredContentSize,
        inlineElements = inlineElements
    ).withProps(props.copy())


    companion object {

        @JvmStatic
        fun newBuilder(contentWidth: Int) = TextBoxBuilder(contentWidth)
    }
}
