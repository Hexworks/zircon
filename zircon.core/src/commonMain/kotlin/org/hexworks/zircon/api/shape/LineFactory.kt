package org.hexworks.zircon.api.shape

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.internal.shape.DefaultShape
import kotlin.math.abs

object LineFactory : ShapeFactory<LineParameters> {

    override fun createShape(shapeParameters: LineParameters): Shape {
        //http://en.wikipedia.org/wiki/Bresenham%27s_line_algorithm
        //Implementation from Graphics Programming Black Book by Michael Abrash
        //Available at http://www.gamedev.net/page/resources/_/technical/graphics-programming-and-theory/graphics-programming-black-book-r1698
        val positions = mutableListOf<Position>()
        var p1 = shapeParameters.fromPoint
        var p2 = shapeParameters.toPoint
        if (p1.y > p2.y) {
            val temp = p1
            p1 = p2
            p2 = temp
        }
        var deltaX = p2.x - p1.x
        val deltaY = p2.y - p1.y
        if (deltaX > 0) {
            if (deltaX > deltaY) {
                positions.addAll(createLine0(p1, deltaX, deltaY, true))
            } else {
                positions.addAll(createLine1(p1, deltaX, deltaY, true))
            }
        } else {
            deltaX = abs(deltaX)
            if (deltaX > deltaY) {
                positions.addAll(createLine0(p1, deltaX, deltaY, false))
            } else {
                positions.addAll(createLine1(p1, deltaX, deltaY, false))
            }
        }
        if (shapeParameters.fromPoint.y > shapeParameters.toPoint.y) {
            positions.reverse()
        }
        return DefaultShape(positions.toSet())
    }

    private fun createLine0(start: Position, deltaX: Int, deltaY: Int, leftToRight: Boolean): Set<Position> {
        val result = mutableSetOf<Position>()
        var (x, y) = start
        val deltaYx2 = deltaY * 2
        val deltaYx2MinusDeltaXx2 = deltaYx2 - (deltaX * 2)
        var errorTerm = deltaYx2 - deltaX
        result.add(Position.create(x, y))
        var dx = deltaX
        while (dx-- > 0) {
            errorTerm += if (errorTerm >= 0) {
                y++
                deltaYx2MinusDeltaXx2
            } else {
                deltaYx2
            }
            x += if (leftToRight) 1 else -1
            result.add(Position.create(x, y))
        }
        return result
    }

    private fun createLine1(start: Position, deltaX: Int, deltaY: Int, leftToRight: Boolean): Set<Position> {
        val result = mutableSetOf<Position>()
        var (x, y) = start
        var dy = deltaY
        val deltaXx2 = deltaX * 2
        val deltaXx2MinusDeltaYx2 = deltaXx2 - (deltaY * 2)
        var errorTerm = deltaXx2 - deltaY
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
