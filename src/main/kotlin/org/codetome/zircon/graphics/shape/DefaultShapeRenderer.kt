package org.codetome.zircon.graphics.shape

import org.codetome.zircon.TerminalPosition
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.terminal.TerminalSize
import java.util.*

/**
 * Default implementation of [ShapeRenderer].
 */
class DefaultShapeRenderer : ShapeRenderer {

    interface Callback {
        fun onPoint(column: Int, row: Int, character: TextCharacter)
    }

    private lateinit var callback: Callback

    fun setCallback(callback: Callback) {
        this.callback = callback
    }

    override fun drawLine(fromPoint: TerminalPosition, toPoint: TerminalPosition, character: TextCharacter) {
        var p1 = fromPoint
        var p2 = toPoint
        //http://en.wikipedia.org/wiki/Bresenham%27s_line_algorithm
        //Implementation from Graphics Programming Black Book by Michael Abrash
        //Available at http://www.gamedev.net/page/resources/_/technical/graphics-programming-and-theory/graphics-programming-black-book-r1698
        if (p1.row > p2.row) {
            val temp = p1
            p1 = p2
            p2 = temp
        }
        var deltaX = p2.column - p1.column
        val deltaY = p2.row - p1.row
        if (deltaX > 0) {
            if (deltaX > deltaY) {
                drawLine0(p1, deltaX, deltaY, true, character)
            } else {
                drawLine1(p1, deltaX, deltaY, true, character)
            }
        } else {
            deltaX = Math.abs(deltaX)
            if (deltaX > deltaY) {
                drawLine0(p1, deltaX, deltaY, false, character)
            } else {
                drawLine1(p1, deltaX, deltaY, false, character)
            }
        }
    }

    private fun drawLine0(start: TerminalPosition, deltaX: Int, deltaY: Int, leftToRight: Boolean, character: TextCharacter) {
        var dx = deltaX
        var x = start.column
        var y = start.row
        val deltaYx2 = deltaY * 2
        val deltaYx2MinusDeltaXx2 = deltaYx2 - dx * 2
        var errorTerm = deltaYx2 - dx
        callback.onPoint(x, y, character)
        while (dx-- > 0) {
            if (errorTerm >= 0) {
                y++
                errorTerm += deltaYx2MinusDeltaXx2
            } else {
                errorTerm += deltaYx2
            }
            x += if (leftToRight) 1 else -1
            callback.onPoint(x, y, character)
        }
    }

    private fun drawLine1(start: TerminalPosition, deltaX: Int, deltaY: Int, leftToRight: Boolean, character: TextCharacter) {
        var dy = deltaY
        var x = start.column
        var y = start.row
        val deltaXx2 = deltaX * 2
        val deltaXx2MinusDeltaYx2 = deltaXx2 - dy * 2
        var errorTerm = deltaXx2 - dy
        callback.onPoint(x, y, character)
        while (dy-- > 0) {
            if (errorTerm >= 0) {
                x += if (leftToRight) 1 else -1
                errorTerm += deltaXx2MinusDeltaYx2
            } else {
                errorTerm += deltaXx2
            }
            y++
            callback.onPoint(x, y, character)
        }
    }

    override fun drawTriangle(p1: TerminalPosition, p2: TerminalPosition, p3: TerminalPosition, character: TextCharacter) {
        drawLine(p1, p2, character)
        drawLine(p2, p3, character)
        drawLine(p3, p1, character)
    }

    override fun drawRectangle(topLeft: TerminalPosition, size: TerminalSize, character: TextCharacter) {
        val topRight = topLeft.withRelativeColumn(size.columns - 1)
        val bottomRight = topRight.withRelativeRow(size.rows - 1)
        val bottomLeft = topLeft.withRelativeRow(size.rows - 1)
        drawLine(topLeft, topRight, character)
        drawLine(topRight, bottomRight, character)
        drawLine(bottomRight, bottomLeft, character)
        drawLine(bottomLeft, topLeft, character)
    }

    override fun fillTriangle(p1: TerminalPosition, p2: TerminalPosition, p3: TerminalPosition, character: TextCharacter) {
        // The algorithm described here is used
        // http://www-users.mat.uni.torun.pl/~wrona/3d_tutor/tri_fillers.html
        val points = arrayOf(p1, p2, p3)
        Arrays.sort(points, { o1, o2 -> if (o1.row < o2.row) -1 else if (o1.row == o2.row) 0 else 1 })

        val dx1: Float
        val dx2: Float
        val dx3: Float
        if (points[1].row - points[0].row > 0) {
            dx1 = (points[1].column - points[0].column).toFloat() / (points[1].row - points[0].row).toFloat()
        } else {
            dx1 = 0f
        }
        if (points[2].row - points[0].row > 0) {
            dx2 = (points[2].column - points[0].column).toFloat() / (points[2].row - points[0].row).toFloat()
        } else {
            dx2 = 0f
        }
        if (points[2].row - points[1].row > 0) {
            dx3 = (points[2].column - points[1].column).toFloat() / (points[2].row - points[1].row).toFloat()
        } else {
            dx3 = 0f
        }

        var startX: Float
        var startY: Float
        var endX: Float
        endX = points[0].column.toFloat()
        startX = endX
        startY = points[0].row.toFloat()
        if (dx1 > dx2) {
            while (startY <= points[1].row) {
                drawLine(TerminalPosition(startX.toInt(), startY.toInt()), TerminalPosition(endX.toInt(), startY.toInt()), character)
                startY++
                startX += dx2
                endX += dx1
            }
            endX = points[1].column.toFloat()
            while (startY <= points[2].row) {
                drawLine(TerminalPosition(startX.toInt(), startY.toInt()), TerminalPosition(endX.toInt(), startY.toInt()), character)
                startY++
                startX += dx2
                endX += dx3
            }
        } else {
            while (startY <= points[1].row) {
                drawLine(TerminalPosition(startX.toInt(), startY.toInt()), TerminalPosition(endX.toInt(), startY.toInt()), character)
                startY++
                startX += dx1
                endX += dx2
            }
            startX = points[1].column.toFloat()
            startY = points[1].row.toFloat()
            while (startY <= points[2].row) {
                drawLine(TerminalPosition(startX.toInt(), startY.toInt()), TerminalPosition(endX.toInt(), startY.toInt()), character)
                startY++
                startX += dx3
                endX += dx2
            }
        }
    }

    override fun fillRectangle(topLeft: TerminalPosition, size: TerminalSize, character: TextCharacter) {
        (0..size.rows - 1).forEach { y ->
            (0..size.columns - 1).forEach {
                x ->
                callback.onPoint(topLeft.column + x, topLeft.row + y, character)
            }
        }
    }
}
