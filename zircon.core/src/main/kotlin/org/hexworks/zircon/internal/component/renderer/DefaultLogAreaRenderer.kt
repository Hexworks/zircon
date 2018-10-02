package org.hexworks.zircon.internal.component.renderer

import org.hexworks.zircon.api.component.LogArea
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.SubTileGraphics
import org.hexworks.zircon.api.graphics.TextWrap
import org.hexworks.zircon.internal.component.impl.log.LogElement
import org.hexworks.zircon.internal.component.impl.log.RenderedPositionArea

class DefaultLogAreaRenderer : ComponentRenderer<LogArea>() {

    private lateinit var visibleRenderArea: VisibleRenderArea

    override fun render(tileGraphics: SubTileGraphics, context: ComponentRenderContext<LogArea>) {
        context.component.getLogElementBuffer().clearLogRenderPositions()
        visibleRenderArea = VisibleRenderArea(context.component.visibleOffset(), context.component.visibleSize())

        val style = context.componentStyle().getCurrentStyle()
        val component = context.component
        tileGraphics.applyStyle(style)
        val logElements = component.getLogElementBuffer().getAllLogElements()
        var currentLogElementY = 0
        var currentY = 0
        tileGraphics.clear()
        logElements.forEach { element ->
            currentY = renderLogElement(tileGraphics, context, element, currentY, currentLogElementY)
            currentLogElementY = element.getPosition().y
        }
    }

    private fun renderLogElement(tileGraphics: SubTileGraphics, context: ComponentRenderContext<LogArea>,
                                 logElement: LogElement, targetYPosition: Int, logElementY: Int): Int {
        var currentPosX = logElement.getPosition().x
        var currentPosY = targetYPosition
        var currentLogElementY = logElementY

        val logElementRenderInfo = mutableListOf<Pair<Position, Int>>()
        getWordsOfLogElement(logElement)
                .forEach { word ->
                    if (logElement.getPosition().y > currentLogElementY)
                        currentPosY += logElement.getPosition().y - currentLogElementY
                    if (context.component.textWrapMode == TextWrap.WORD_WRAP && (currentPosX + word.length) > tileGraphics.size().width()) {
                        currentPosX = 0
                        currentPosY += 1
                    }

                    if (logElement.modifiers != null)
                        tileGraphics.enableModifiers(logElement.modifiers!!)

                    val position = Position.create(currentPosX, currentPosY)
                    if (visibleRenderArea.contains(position)) {
                        renderWord(position, context, tileGraphics, word, logElementRenderInfo, logElement)

                        currentPosX += word.length
                        currentLogElementY = logElement.getPosition().y
                    }
                }

        if (logElementRenderInfo.isNotEmpty()) {
            val startRenderPosition = logElementRenderInfo.first().first
            val endRenderPosition = logElementRenderInfo.last().first.plus(Position.create(logElementRenderInfo.last().second, 0))
            logElement.renderedPositionArea = RenderedPositionArea(startRenderPosition, endRenderPosition, context.component.contentSize())
        }
        return currentPosY
    }

    private fun renderWord(position: Position, context: ComponentRenderContext<LogArea>, tileGraphics: SubTileGraphics,
                           word: String, logElementRenderInfo: MutableList<Pair<Position, Int>>, logElement: LogElement) {
        val visiblePosition = position.minus(context.component.visibleOffset())
        tileGraphics.putText(word, visiblePosition)
        logElementRenderInfo.add(Pair(visiblePosition, word.length))

        if (logElement.modifiers != null)
            tileGraphics.disableModifiers(logElement.modifiers!!)
    }

    private fun getWordsOfLogElement(logElement: LogElement): List<String> {
        return if (logElement.getTextAsString() != "")
            logElement.getTextAsString().split(" ").map { "$it " }
        else
            listOf("")
    }
}

data class VisibleRenderArea(val topLeft: Position, val size: Size) {
    fun contains(position: Position): Boolean {
        return topLeft.x <= position.x && position.x + size.width() >= position.x
                && topLeft.y <= position.y && topLeft.y + size.height() >= topLeft.y

    }

}