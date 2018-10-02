package org.hexworks.zircon.internal.component.impl.log

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.api.modifier.SimpleModifiers
import org.hexworks.zircon.api.shape.LineParameters
import kotlin.math.*

sealed class LogElement(private val xPosition: Int) {
    var modifiers: Set<Modifier>? = null
    var renderedPositionArea: RenderedPositionArea? = null
    lateinit var logElementRow: LogElementRow

    abstract fun getTextAsString(): String

    fun getPosition() = Position.create(xPosition, logElementRow.yPosition)
    fun length() = getTextAsString().length
}

data class TextElement(val text: String, private val xPosition: Int) : LogElement(xPosition) {
    override fun getTextAsString(): String {
        return text
    }
}

data class HyperLinkElement(val linkText: String, val linkId: String, private val xPosition: Int) : LogElement(xPosition) {
    init {
        modifiers = setOf(SimpleModifiers.Underline)
    }

    override fun getTextAsString(): String {
        return linkText
    }
}

data class RenderedPositionArea(val startPosition: Position, val endPosition: Position, val logAreaSize: Size) {
    fun containsPosition(position: Position): Boolean {
        val lines = getLines()

        return lines.any {
            lineDistance(LineParameters(it.fromPoint, position)) + lineDistance(LineParameters(it.toPoint, position)) == lineDistance(LineParameters(it.fromPoint, it.toPoint))
        }
    }

    private fun getLines(): List<LineParameters> {
        val lines = mutableListOf<LineParameters>()
        for (y in startPosition.y..endPosition.y) {
            if (y == startPosition.y) {
                val endPosition = if (startPosition.y != endPosition.y)
                    Position.create(logAreaSize.width(), y)
                else
                    endPosition
                lines.add(LineParameters(startPosition, endPosition))
            } else if (y == endPosition.y && y != startPosition.y) {
                lines.add(LineParameters(Position.create(0, y), endPosition))
            } else
                lines.add(LineParameters(Position.create(0, y), Position.create(logAreaSize.width(), y)))
        }
        return lines.toList()
    }

    private fun lineDistance(lineParameters: LineParameters) = sqrt(((lineParameters.fromPoint.x - lineParameters.toPoint.x).toDouble()).pow(2) +
            ((lineParameters.fromPoint.y - lineParameters.toPoint.y).toDouble()).pow(2))
}

