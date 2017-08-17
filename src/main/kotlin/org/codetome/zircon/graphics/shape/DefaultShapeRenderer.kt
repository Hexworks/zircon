package org.codetome.zircon.graphics.shape

import org.codetome.zircon.Position
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.Size
import org.codetome.zircon.graphics.shape.test.ShapeRenderer

/**
 * Default implementation of [ShapeRenderer].
 */
class DefaultShapeRenderer : ShapeRenderer {

    interface Callback {
        fun onPoint(position: Position, character: TextCharacter)
    }

    private lateinit var callback: Callback

    fun setCallback(callback: Callback) {
        this.callback = callback
    }

    override fun drawLine(fromPoint: Position, toPoint: Position, character: TextCharacter) {
        ShapeFactory.createLine(fromPoint, toPoint).forEach {
            callback.onPoint(it, character)
        }
    }

    override fun drawTriangle(p1: Position, p2: Position, p3: Position, character: TextCharacter) {
        ShapeFactory.createTriangle(p1, p2, p3).forEach {
            callback.onPoint(it, character)
        }
    }

    override fun drawRectangle(topLeft: Position, size: Size, character: TextCharacter) {
        ShapeFactory.createRectangle(topLeft, size).forEach {
            callback.onPoint(it, character)
        }
    }

    override fun fillTriangle(p1: Position, p2: Position, p3: Position, character: TextCharacter) {
        ShapeFactory.createFilledTriangle(p1, p2, p3).forEach {
            callback.onPoint(it, character)
        }
    }

    override fun fillRectangle(topLeft: Position, size: Size, character: TextCharacter) {
        ShapeFactory.createFilledRectangle(topLeft, size).forEach {
            callback.onPoint(it, character)
        }
    }
}
