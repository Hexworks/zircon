package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.LogArea
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.graphics.SubTileGraphics
import org.hexworks.zircon.api.graphics.TextWrap
import org.hexworks.zircon.internal.component.impl.log.LogElement
import org.hexworks.zircon.internal.component.impl.log.RenderedPositionArea

class DefaultLogAreaRenderer : ComponentRenderer<LogArea>() {

    override fun render(tileGraphics: SubTileGraphics, context: ComponentRenderContext<LogArea>) {
        val style = context.componentStyle().getCurrentStyle()
        val component = context.component
        tileGraphics.applyStyle(style)
        val logElements = component.getLogElementBuffer().getAllLogElements()
        var currentLogElementY = 0
        var currentY = 0
        tileGraphics.clear()
        logElements.forEach { element ->
            currentY = renderLogElement(tileGraphics, context, element, currentY, currentLogElementY)
            currentLogElementY = element.position.y
        }
    }

    private fun renderLogElement(tileGraphics: SubTileGraphics, context: ComponentRenderContext<LogArea>,
                                 logElement: LogElement, targetYPosition: Int, logElementY: Int): Int {
        var currentX = logElement.position.x
        var currentY = targetYPosition
        var currentLogElementY = logElementY

        val words = if (logElement.getTextAsString() != "")
            logElement.getTextAsString().split(" ").map { "$it " }
        else
            listOf("")

        val logElementRenderInfo = mutableListOf<Pair<Position, Int>>()
        words.forEach { word ->
            if (logElement.position.y > currentLogElementY)
                currentY += logElement.position.y - currentLogElementY
            if (context.component.textWrap == TextWrap.WORD_WRAP && (currentX + word.length) > tileGraphics.size().width()) {
                currentX = 0
                currentY += 1
            }

            if (logElement.modifiers != null)
                tileGraphics.enableModifiers(logElement.modifiers!!)

            val position = Position.create(currentX, currentY).plus(context.component.visibleOffset())
            tileGraphics.putText(word, position)
            logElementRenderInfo.add(Pair(position, word.length))

            if (logElement.modifiers != null)
                tileGraphics.disableModifiers(logElement.modifiers!!)

            currentX += word.length
            currentLogElementY = logElement.position.y
        }

        val startRenderPosition = logElementRenderInfo.first().first
        val endRenderPosition = logElementRenderInfo.last().first.plus(Position.create(logElementRenderInfo.last().second, 0))
        logElement.renderedPositionArea = RenderedPositionArea(startRenderPosition, endRenderPosition, context.component.contentSize())
        return currentY
    }
}