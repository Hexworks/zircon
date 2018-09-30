package org.hexworks.zircon.internal.component.impl.log

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.api.modifier.SimpleModifiers
import kotlin.math.abs

abstract class LogElement(open val position: Position) {
    var modifiers: Set<Modifier>? = null
    var renderedPositionArea: RenderedPositionArea? = null

    abstract fun getTextAsString(): String


    fun length() = getTextAsString().length
}

data class TextElement(val text: String, override val position: Position) : LogElement(position) {
    override fun getTextAsString(): String {
        return text
    }
}

data class HyperLinkElement(val linkText: String, val linkId: String, override val position: Position) : LogElement(position) {

    init {
        modifiers = setOf(SimpleModifiers.Underline)
    }

    override fun getTextAsString(): String {
        return linkText
    }
}

data class RenderedPositionArea(val startPosition: Position, val endPosition: Position) {
    fun containsPosition(position: Position): Boolean {
        val dotproduct = (position.x - startPosition.x) * (endPosition.x - startPosition.x) + (position.y - startPosition.y) * (endPosition.y - startPosition.y)
        if (dotproduct < 0)
            return false

        val squaredlength = (endPosition.x - startPosition.x) * (endPosition.x - startPosition.x) + (endPosition.y - startPosition.y) * (endPosition.y - startPosition.y)
        if (dotproduct > squaredlength)
            return false

        return true
    }
}

