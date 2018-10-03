package org.hexworks.zircon.internal.component.impl.log

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.LogArea
import org.hexworks.zircon.api.component.renderer.ComponentRenderContext
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.SubTileGraphics
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.internal.component.renderer.PositionRenderContext
import org.hexworks.zircon.internal.component.renderer.VisibleRenderArea

sealed class LogElement(private val xPosition: Int) {
    var modifiers: Set<Modifier>? = null
    var renderedPositionArea: RenderedPositionArea? = null
    lateinit var logElementRow: LogElementRow

    abstract fun getSize(): Size
    abstract fun render(tileGraphics: SubTileGraphics, context: ComponentRenderContext<LogArea>,
                        positionRenderContext: PositionRenderContext): Int

    fun getPosition() = Position.create(xPosition, logElementRow.yPosition)

}

data class LogTextElement(val text: String, private val xPosition: Int) : LogElement(xPosition) {
    override fun getSize(): Size {
        return Size.create(text.length, 1)
    }

    override fun render(tileGraphics: SubTileGraphics, context: ComponentRenderContext<LogArea>,
                        positionRenderContext: PositionRenderContext): Int {
        val visibleRenderArea = VisibleRenderArea(context.component.visibleOffset(), context.component.visibleSize())
        var currentPosX = getPosition().x
        var currentPosY = positionRenderContext.currentScreenPosY
        var currentLogElementY = positionRenderContext.currentLogElementPosY

        val logElementRenderInfo = mutableListOf<Pair<Position, Int>>()
        getWordsOfLogElement()
                .forEach { word ->
                    if (getPosition().y > currentLogElementY)
                        currentPosY += getPosition().y - currentLogElementY
                    if (context.component.wrapLogElements && (currentPosX + word.length) > tileGraphics.size().width()) {
                        currentPosX = 0
                        currentPosY += 1
                    }

                    if (modifiers != null)
                        tileGraphics.enableModifiers(modifiers!!)

                    val position = Position.create(currentPosX, currentPosY)
                    logElementRenderInfo.add(Pair(position, word.length))
                    if (visibleRenderArea.contains(position)) {
                        renderWord(position, context, tileGraphics, word)

                        currentPosX += word.length
                        currentLogElementY = getPosition().y
                    }
                }

        if (logElementRenderInfo.isNotEmpty()) {
            val startRenderPosition = logElementRenderInfo.first().first
            val endRenderPosition = logElementRenderInfo.last().first.plus(Position.create(logElementRenderInfo.last().second, 0))
            renderedPositionArea = RenderedPositionArea(startRenderPosition, endRenderPosition)
        }
        return currentPosY
    }

    private fun renderWord(position: Position, context: ComponentRenderContext<LogArea>, tileGraphics: SubTileGraphics,
                           word: String) {
        val visiblePosition = position.minus(context.component.visibleOffset())
        tileGraphics.putText(word, visiblePosition)

        if (modifiers != null)
            tileGraphics.disableModifiers(modifiers!!)
    }

    private fun getWordsOfLogElement(): List<String> {
        return if (text != "")
            text.split(" ").map { "$it " }
        else
            listOf("")
    }

}

data class LogComponentElement(val component: Component, private val xPosition: Int) : LogElement(xPosition) {
    override fun getSize(): Size {
        return component.size()
    }

    override fun render(tileGraphics: SubTileGraphics, context: ComponentRenderContext<LogArea>,
                        positionRenderContext: PositionRenderContext): Int {
        VisibleRenderArea(context.component.visibleOffset(), context.component.visibleSize())
        var currentPosX = getPosition().x
        var currentPosY = positionRenderContext.currentScreenPosY
        val currentLogElementY = positionRenderContext.currentLogElementPosY

        if (getPosition().y > currentLogElementY)
            currentPosY += getPosition().y - currentLogElementY
        if (context.component.wrapLogElements && (currentPosX + component.size().width()) > tileGraphics.size().width()) {
            currentPosX = 0
            currentPosY += 1
        }

        val position = Position.create(currentPosX, currentPosY).minus(context.component.visibleOffset())
        component.moveTo(position)
        currentPosX += component.size().width()

        renderedPositionArea = RenderedPositionArea(position, position.plus(Position.create(component.size().width(), position.y)))
        return currentPosY
    }

}

data class RenderedPositionArea(val startPosition: Position, val endPosition: Position)

