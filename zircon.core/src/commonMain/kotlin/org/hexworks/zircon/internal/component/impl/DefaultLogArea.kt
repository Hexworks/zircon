package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.builder.component.ParagraphBuilder
import org.hexworks.zircon.api.builder.component.TextBoxBuilder
import org.hexworks.zircon.api.component.*
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.extensions.abbreviate

class DefaultLogArea internal constructor(
    componentMetadata: ComponentMetadata,
    renderingStrategy: ComponentRenderingStrategy<LogArea>
) : LogArea, DefaultContainer(
    metadata = componentMetadata,
    renderer = renderingStrategy
) {

    private var currentInlineBuilder = createTextBoxBuilder()
    private val logElements = mutableListOf<AttachedComponent>()

    override fun addHeader(text: String, withNewLine: Boolean) {
        LOGGER.debug("Adding header text ($text) to LogArea (id=${id.abbreviate()}).")
        addLogElement(
            createTextBoxBuilder()
                .addHeader(text, withNewLine)
                .build()
        )
    }

    override fun addParagraph(paragraph: String, withNewLine: Boolean, withTypingEffectSpeedInMs: Long) {
        LOGGER.debug("Adding paragraph text ($paragraph) to LogArea (id=${id.abbreviate()}).")
        addLogElement(
            createTextBoxBuilder()
                .addParagraph(paragraph, withNewLine, withTypingEffectSpeedInMs)
                .build()
        )
    }

    override fun addParagraph(paragraphBuilder: ParagraphBuilder, withNewLine: Boolean) {
        LOGGER.debug("Adding paragraph from builder to LogArea (id=${id.abbreviate()}).")
        addLogElement(
            createTextBoxBuilder()
                .addParagraph(paragraphBuilder, withNewLine)
                .build(), false
        )
    }


    override fun addListItem(item: String) {
        LOGGER.debug("Adding list item ($item) to LogArea (id=${id.abbreviate()}).")
        addLogElement(
            createTextBoxBuilder()
                .addListItem(item)
                .build()
        )
    }

    override fun addInlineText(text: String) {
        LOGGER.debug("Adding inline text ($text) to LogArea (id=${id.abbreviate()}).")
        currentInlineBuilder.addInlineText(text)
    }

    override fun addInlineComponent(component: Component) {
        LOGGER.debug("Adding inline component ($component) to LogArea (id=${id.abbreviate()}).")
        currentInlineBuilder.addInlineComponent(component)
    }

    override fun commitInlineElements() {
        LOGGER.debug("Committing inline elements of LogArea (id=${id.abbreviate()}).")
        val builder = currentInlineBuilder
        currentInlineBuilder = createTextBoxBuilder()
        addLogElement(builder.commitInlineElements().build())
    }

    override fun addNewRows(numberOfRows: Int) {
        LOGGER.debug("Adding new rows ($numberOfRows) to LogArea (id=${id.abbreviate()}).")
        (0 until numberOfRows).forEach { _ ->
            addLogElement(
                createTextBoxBuilder()
                    .addNewLine()
                    .build()
            )
        }
    }

    override fun clear() {
        logElements.forEach { it.detach() }
        logElements.clear()
    }

    private fun addLogElement(element: TextBox, applyTheme: Boolean = true) {
        var currentHeight = children.map { it.height }.fold(0, Int::plus)
        val maxHeight = contentSize.height
        val elementHeight = element.height
        val remainingHeight = maxHeight - currentHeight
        require(elementHeight <= contentSize.height) {
            "Can't add an element which has a bigger height than this LogArea."
        }
        if (currentHeight + elementHeight > maxHeight) {
            val linesToFree = elementHeight - remainingHeight
            var currentFreedSpace = 0
            while (currentFreedSpace < linesToFree) {
                logElements.firstOrNull()?.let { topChild ->
                    currentFreedSpace += topChild.height
                    topChild.detach()
                    logElements.remove(topChild)
                }
            }
            logElements.forEach { child ->
                child.moveUpBy(currentFreedSpace)
            }
            currentHeight -= currentFreedSpace
        }
        element.moveTo(Position.create(0, currentHeight))
        logElements.add(addComponent(element))
        if (applyTheme) {
            element.theme = theme
        }
    }

    override fun convertColorTheme(colorTheme: ColorTheme) = colorTheme.toContainerStyle()

    private fun createTextBoxBuilder(): TextBoxBuilder {
        return TextBoxBuilder
            .newBuilder(contentSize.width)
            .withTileset(tileset)
    }

    companion object {
        val LOGGER = LoggerFactory.getLogger(LogArea::class)
    }
}
