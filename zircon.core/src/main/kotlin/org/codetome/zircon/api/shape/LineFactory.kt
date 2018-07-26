package org.codetome.zircon.api.shape

import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.util.Math
import org.codetome.zircon.internal.graphics.DefaultShape

object LineFactory : ShapeFactory<LineParameters> {

    override fun createShape(shapeParameters: LineParameters): Shape {
        var p1 = shapeParameters.fromPoint
        var p2 = shapeParameters.toPoint
        //http://en.wikipedia.org/wiki/Bresenham%27s_line_algorithm
        //Implementation from Graphics Programming Black Book by Michael Abrash
        //Available at http://www.gamedev.net/page/resources/_/technical/graphics-programming-and-theory/graphics-programming-black-book-r1698
        if (p1.y > p2.y) {
            val temp = p1
            p1 = p2
            p2 = temp
        }
        var deltaX = p2.x - p1.x
        val deltaY = p2.y - p1.y
        return if (deltaX > 0) {
            if (deltaX > deltaY) {
                DefaultShape(createLine0(p1, deltaX, deltaY, true))
            } else {
                DefaultShape(createLine1(p1, deltaX, deltaY, true))
            }
        } else {
            deltaX = Math.abs(deltaX)
            if (deltaX > deltaY) {
                DefaultShape(createLine0(p1, deltaX, deltaY, false))
            } else {
                DefaultShape(createLine1(p1, deltaX, deltaY, false))
            }
        }
    }

    private fun createLine0(start: Position, deltaX: Int, deltaY: Int, leftToRight: Boolean): Set<Position> {
        val result = mutableSetOf<Position>()
        var dx = deltaX
        var (x, y) = start
        val deltaYx2 = deltaY * 2
        val deltaYx2MinusDeltaXx2 = deltaYx2 - dx * 2
        var errorTerm = deltaYx2 - dx
        result.add(Position.create(x, y))
        while (dx-- > 0) {
            if (errorTerm >= 0) {
                y++
                errorTerm += deltaYx2MinusDeltaXx2
            } else {
                errorTerm += deltaYx2
            }
            x += if (leftToRight) 1 else -1
            result.add(Position.create(x, y))
        }
        return result
    }

    private fun createLine1(start: Position, deltaX: Int, deltaY: Int, leftToRight: Boolean): Set<Position> {
        val result = mutableSetOf<Position>()
        var dy = deltaY
        var (x, y) = start
        val deltaXx2 = deltaX * 2
        val deltaXx2MinusDeltaYx2 = deltaXx2 - dy * 2
        var errorTerm = deltaXx2 - dy
        result.add(Position.create(x, y))
        while (dy-- > 0) {
            if (errorTerm >= 0) {
                x += if (leftToRight) 1 else -1
                errorTerm += deltaXx2MinusDeltaYx2
            } else {
                errorTerm += deltaXx2
            }
            y++
            result.add(Position.create(x, y))
        }
        return result
    }

    fun buildLine(lineParams: LineParameters) = createShape(lineParams)

    fun buildLine(fromPoint: Position, toPoint: Position) = buildLine(LineParameters(fromPoint, toPoint))
}
