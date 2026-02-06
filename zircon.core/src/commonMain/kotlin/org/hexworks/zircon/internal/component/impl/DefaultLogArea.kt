package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.builder.component.buildHbox
import org.hexworks.zircon.api.builder.component.buildLabel
import org.hexworks.zircon.api.component.AttachedComponent
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.LogArea
import org.hexworks.zircon.api.component.builder.base.withPreferredSize
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

    private val logElements = mutableListOf<AttachedComponent>()

    override fun addNewRows(numberOfRows: Int) {
        LOGGER.debug { "Adding new rows ($numberOfRows) to LogArea (id=${id.abbreviate()})." }
        addLogElement(
            buildLabel {
                withPreferredSize {
                    width = 1
                    height = numberOfRows
                }
            }
        )
    }

    override fun addEmptyLine() {
        addLogElement(buildLabel { })
    }

    override fun addInlineRow(row: List<Component>, applyTheme: Boolean) {
        val width = size.width
        addLogElement(buildHbox {
            withPreferredSize {
                this.width = width
                height = 1
            }
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
        element.moveTo(Position.create(0, currentHeight))
        logElements.add(addComponent(element))
        if (applyTheme) {
            element.theme = theme
        }
    }

    override fun convertColorTheme(colorTheme: ColorTheme) = colorTheme.toContainerStyle()

    companion object {
        val LOGGER = LoggerFactory.getLogger(LogArea::class)
    }
}
