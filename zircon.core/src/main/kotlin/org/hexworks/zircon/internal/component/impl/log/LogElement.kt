package org.hexworks.zircon.internal.component.impl.log

import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.SubTileGraphics
import org.hexworks.zircon.api.modifier.Delay
import org.hexworks.zircon.api.modifier.FadeIn
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.internal.component.renderer.LogElementRenderContext
import org.hexworks.zircon.internal.component.renderer.LogElementRenderResult
import org.hexworks.zircon.internal.component.renderer.VisibleRenderArea

sealed class LogElement(private val xPosition: Int) {
    var modifiers: Set<Modifier> = setOf()
    var screenPositionArea: ScreenPositionArea? = null
    lateinit var logElementRow: LogElementRow

    abstract fun getSize(): Size
    abstract fun render(tileGraphics: SubTileGraphics, context: LogElementRenderContext): LogElementRenderResult

    fun getPosition() = Position.create(xPosition, logElementRow.yPosition)

}

data class LogTextElement(val text: String, private val xPosition: Int) : LogElement(xPosition) {
    override fun getSize(): Size {
        return Size.create(text.length, 1)
    }

    override fun render(tileGraphics: SubTileGraphics, context: LogElementRenderContext): LogElementRenderResult {
        val component = context.componentRenderContext.component
        val visibleRenderArea = VisibleRenderArea(component.visibleOffset, component.visibleSize)
        var currentScreenPosX = getPosition().x
        var currentScreenPosY = context.positionRenderContext.currentScreenPosY
        var currentLogElementY = context.positionRenderContext.currentLogElementPosY
        val logElementRenderInfo = mutableListOf<Pair<Position, Int>>()
        var delayInMs = context.delayTimeInMs

        getWordsOfLogElement()
                .forEach { word ->
                    if (getPosition().y > currentLogElementY)
                        currentScreenPosY += getPosition().y - currentLogElementY
                    if (component.wrapLogElements && (currentScreenPosX + word.length) > tileGraphics.size.width()) {
                        currentScreenPosX = 0
                        currentScreenPosY += 1
                    }
//
//                    if (modifiers != null)
//                        tileGraphics.enableModifiers(modifiers!!)

                    val position = Position.create(currentScreenPosX, currentScreenPosY)
                    logElementRenderInfo.add(Pair(position, word.length))
                    if (visibleRenderArea.contains(position)) {
                        val wordContext = LogElementRenderContext(context.componentRenderContext, context.positionRenderContext, delayInMs)
                        renderWord(position, wordContext, tileGraphics, word)
                        delayInMs += word.length * component.delayInMsForTypewriterEffect
                        currentScreenPosX += word.length
                        currentLogElementY = getPosition().y
                    }
                }

        if (logElementRenderInfo.isNotEmpty()) {
            val startRenderPosition = logElementRenderInfo.first().first
            val endRenderPosition = logElementRenderInfo.last().first.plus(Position.create(logElementRenderInfo.last().second, 0))
            screenPositionArea = ScreenPositionArea(startRenderPosition, endRenderPosition)
        }

        return LogElementRenderResult(currentScreenPosY, delayInMs)
    }

    private fun renderWord(position: Position, context: LogElementRenderContext, tileGraphics: SubTileGraphics,
                           word: String) {
        val visiblePosition = position.minus(context.componentRenderContext.component.visibleOffset)
        tileGraphics.putText(word, visiblePosition)
        var delayTimeInMs = context.delayTimeInMs
        word.forEachIndexed { col, char ->
            val wordModifiers = modifiers.plus(Delay(delayTimeInMs))
            tileGraphics.setTileAt(position.withRelativeX(col), TileBuilder
                    .newBuilder()
                    .character(char)
                    .modifiers(wordModifiers)
                    .build())
            delayTimeInMs += context.componentRenderContext.component.delayInMsForTypewriterEffect
        }
//        if (modifiers != null)
//            tileGraphics.disableModifiers(modifiers!!)
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
        return component.size
    }

    override fun render(tileGraphics: SubTileGraphics, context: LogElementRenderContext): LogElementRenderResult {
        val component = context.componentRenderContext.component
        VisibleRenderArea(component.visibleOffset, component.visibleSize)
        var currentScreenPosX = getPosition().x
        var currentScreenPosY = context.positionRenderContext.currentScreenPosY
        val currentLogElementY = context.positionRenderContext.currentLogElementPosY

        if (getPosition().y > currentLogElementY)
            currentScreenPosY += getPosition().y - currentLogElementY
        if (component.wrapLogElements && (currentScreenPosX + component.size.width()) > tileGraphics.size.width()) {
            currentScreenPosX = 0
            currentScreenPosY += 1
        }

        val position = Position.create(currentScreenPosX, currentScreenPosY).minus(component.visibleOffset)
        component.moveTo(position)
        currentScreenPosX += component.size.width()

        screenPositionArea = ScreenPositionArea(position, position.plus(Position.create(component.size.width(), position.y)))
        return LogElementRenderResult(currentScreenPosY)
    }

}

data class ScreenPositionArea(val startPosition: Position, val endPosition: Position)

