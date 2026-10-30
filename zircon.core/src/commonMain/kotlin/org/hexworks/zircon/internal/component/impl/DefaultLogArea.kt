package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.logging.api.Logger
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.data.Size
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.behavior.extensions.height
import org.hexworks.zircon.api.behavior.extensions.moveUpBy
import org.hexworks.zircon.api.builder.component.hBox
import org.hexworks.zircon.api.builder.component.label
import org.hexworks.zircon.api.component.AttachedComponent
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.LogArea
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.extensions.abbreviate

public class DefaultLogArea internal constructor(
    componentMetadata: ComponentMetadata,
    renderingStrategy: ComponentRenderingStrategy<LogArea>
) : LogArea, DefaultContainer(
    metadata = componentMetadata,
    renderer = renderingStrategy
) {

    private val logElements = mutableListOf<AttachedComponent>()

    override fun addNewRows(numberOfRows: Int) {
        LOGGER.debug { "Adding new rows ($numberOfRows) to LogArea (id=${id.abbreviate()})." }
        addLogElement(
            label {
                preferredSize = Size(1, numberOfRows)
            }
        )
    }

    override fun addEmptyLine() {
        addLogElement(label { })
    }

    override fun addInlineRow(row: List<Component>, applyTheme: Boolean) {
        // The row lives inside the decorations, so it is as wide as the content, not the component.
        val width = contentSize.width
        addLogElement(hBox {
            preferredSize = Size(width, 1)
            children {
                row.forEach {
                    +it
                }
            }
        }, applyTheme)
    }

    override fun addRow(row: Component, applyTheme: Boolean) {
        addLogElement(row, applyTheme)
    }

    override fun clear() {
        logElements.forEach { it.detach() }
        logElements.clear()
    }

    private fun addLogElement(element: Component, applyTheme: Boolean = true) {
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
        element.moveTo(Position(0, currentHeight))
        logElements.add(addComponent(element))
        if (applyTheme) {
            element.theme = theme
        }
    }

    override fun convertColorTheme(colorTheme: ColorTheme): ComponentStyleSet = colorTheme.toContainerStyle()

    public companion object {
        public val LOGGER: Logger = LoggerFactory.getLogger(LogArea::class)
    }
}
