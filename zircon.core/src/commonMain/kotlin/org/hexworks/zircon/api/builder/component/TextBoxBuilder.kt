package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.TextBox
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.dsl.buildChildFor
import org.hexworks.zircon.internal.component.impl.DefaultTextBox
import org.hexworks.zircon.internal.component.renderer.DefaultTextBoxRenderer
import org.hexworks.zircon.internal.dsl.ZirconDsl


@ZirconDsl
class TextBoxBuilder(
    initialContentWidth: Int
) : BaseComponentBuilder<TextBox>(DefaultTextBoxRenderer()) {

    private var nextPosition: Position = Position.defaultPosition()
    private val components: MutableList<Component> = mutableListOf()
    private val inlineElements: MutableList<Component> = mutableListOf()

    init {
        props.preferredContentSize = Size.unknown().withWidth(initialContentWidth)
    }

    override var preferredSize: Size
        get() = props.preferredSize
        set(value) {
            throw UnsupportedOperationException("You can't set a size for a TextBox by hand. Try setting width instead.")
        }

    override var preferredContentSize: Size
        get() = props.preferredContentSize
        set(value) {
            // TODO: explain why this sets preferred size
            props.preferredSize = value
        }

    fun addNewLine() = also {
        nextPosition = nextPosition.withRelativeY(1)
        fixHeight(1)
    }

    /**
     * Adds block level components to this text box.
     */
    fun addInlineComponents(
        components: List<Component>
    ): TextBoxBuilder = also {
        components.forEach(::addInlineComponent)
        commitInlineElements()
    }

    /**
     * Adds block level components to this text box.
     */
    fun addBlockComponents(
        blockComponents: BlockComponents
    ): TextBoxBuilder = also {
        val (components, addNewLine) = blockComponents;
        components.forEach { component ->
            require(component.width <= contentWidth) {
                "Child width (${component.width} must be less than or equal to content width (${contentWidth})."
            }
            component.moveTo(nextPosition)
            this.components.add(component)
            updateSizeAndPosition(component.height)
            if (addNewLine) {
                addNewLine()
            }
        }
    }

    private fun addInlineComponent(component: Component) = also {
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

    private fun commitInlineElements() = also {
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
}

data class BlockComponents(
    val components: List<Component>,
    val addNewLine: Boolean
)

@ZirconDsl
class InlineComponentsBuilder : Builder<List<Component>> {
    var components = mutableListOf<Component>()

    operator fun Component.unaryPlus() {
        components.add(this)
    }

    override fun build(): List<Component> {
        return components.toList()
    }
}

@ZirconDsl
class BlockComponentsBuilder : Builder<BlockComponents> {
    var components = mutableListOf<Component>()
    var addNewLine: Boolean = true

    operator fun Component.unaryPlus() {
        components.add(this)
    }

    override fun build(): BlockComponents {
        return BlockComponents(components.toList(), addNewLine)
    }
}

/**
 * Creates a new [TextBox] using the component builder DSL and returns it.
 */
fun buildTextBox(
    initialContentWidth: Int,
    init: TextBoxBuilder.() -> Unit
): TextBox = TextBoxBuilder(initialContentWidth).apply(init).build()

/**
 * Creates a new [TextBox] using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [TextBox].
 */
fun <T : BaseContainerBuilder<*>> T.textBox(
    initialWidth: Int,
    init: TextBoxBuilder.() -> Unit
): TextBox = buildChildFor(this, TextBoxBuilder(initialWidth), init)


fun TextBoxBuilder.blocks(
    init: BlockComponentsBuilder.() -> Unit
) = addBlockComponents(BlockComponentsBuilder().apply(init).build())

fun TextBoxBuilder.inline(
    init: InlineComponentsBuilder.() -> Unit
) = addInlineComponents(InlineComponentsBuilder().apply(init).build())

