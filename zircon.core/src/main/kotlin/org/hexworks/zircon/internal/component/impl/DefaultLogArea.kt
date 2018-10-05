package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.builder.component.ColorThemeBuilder
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.component.TextBoxBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.component.*
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.resource.TilesetResource

class DefaultLogArea constructor(
        private val renderingStrategy: ComponentRenderingStrategy<LogArea>,
        position: Position,
        size: Size,
        tileset: TilesetResource,
        componentStyleSet: ComponentStyleSet,
        private val delayInMsForTypewriterEffect: Int = 100)
    : LogArea, DefaultContainer(
        position = position,
        size = size,
        tileset = tileset,
        componentStyles = componentStyleSet,
        renderer = renderingStrategy) {

    private var currentInlineBuilder = createTextBoxBuilder()
    private var currentTheme: ColorTheme = ColorThemeBuilder.newBuilder().build()

    init {
        render()
    }

    override fun addHeader(text: String, withNewLine: Boolean) {
        addLogElement(createTextBoxBuilder()
                .header(text, withNewLine)
                .build())
    }

    override fun addParagraph(paragraph: String, withNewLine: Boolean, withTypingEffect: Boolean) {
        addLogElement(createTextBoxBuilder()
                .paragraph(paragraph, withNewLine, withTypingEffect)
                .build())
    }

    override fun addListItem(item: String) {
        addLogElement(createTextBoxBuilder()
                .listItem(item)
                .build())
    }

    override fun addInlineText(text: String) {
        currentInlineBuilder.inlineText(text)
    }

    override fun addInlineComponent(component: Component) {
        currentInlineBuilder.inlineComponent(component)
    }

    override fun commitInlineElements() {
        val builder = currentInlineBuilder
        currentInlineBuilder = createTextBoxBuilder()
        addLogElement(builder.commitInlineElements()
                .build())
    }

    override fun addNewRows(numberOfRows: Int) {
        (0 until numberOfRows).forEach { _ ->
            addLogElement(createTextBoxBuilder()
                    .newLine()
                    .build())
        }
    }

    override fun clear() {
        children.forEach { removeComponent(it) }
    }

    override fun applyColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        currentTheme = colorTheme
        return ComponentStyleSetBuilder.newBuilder()
                .defaultStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.secondaryForegroundColor)
                        .backgroundColor(colorTheme.primaryBackgroundColor)
                        .build())
                .disabledStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.secondaryForegroundColor)
                        .backgroundColor(colorTheme.secondaryBackgroundColor)
                        .build())
                .focusedStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.primaryBackgroundColor)
                        .backgroundColor(colorTheme.primaryForegroundColor)
                        .build())
                .build().also { css ->
                    componentStyleSet = css
                    render()
                    children.forEach {
                        it.applyColorTheme(colorTheme)
                    }
                }
    }

    private fun addLogElement(element: TextBox) {
        val currentHeight = children.asSequence().map { it.height }.fold(1, Int::plus)
        val maxHeight = height
        val elementHeight = element.height
        val remainingHeight = maxHeight - currentHeight
        require(elementHeight <= height) {
            "Can't add an element which has a bigger height than this LogArea."
        }
        if (currentHeight + elementHeight > maxHeight) {
            val linesToFree = elementHeight - remainingHeight
            var currentFreedSpace = 0
            while (currentFreedSpace < linesToFree) {
                children.firstOrNull()?.let { topChild ->
                    topChild.removeFromParent()
                    currentFreedSpace += topChild.height
                }
            }
            children.forEach { child ->
                child.moveUpBy(currentFreedSpace)
            }
        }
        children.lastOrNull()?.let { lastChild ->
            element.moveTo(Position.zero().relativeToBottomOf(lastChild))
        }
        addComponent(element)
        element.applyColorTheme(currentTheme)
        render()
    }

    private fun createTextBoxBuilder(): TextBoxBuilder {
        return TextBoxBuilder
                .newBuilder()
                .contentWidth(width)
    }

    override fun render() {
        renderingStrategy.render(this, tileGraphics)
    }

}
