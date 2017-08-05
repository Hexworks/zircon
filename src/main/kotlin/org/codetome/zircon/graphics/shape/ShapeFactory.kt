package org.codetome.zircon.graphics.shape

import org.codetome.zircon.Position
import org.codetome.zircon.terminal.Size
import java.util.*

object ShapeFactory {

    /**
     * Creates the points for a line from a specified position to a specified position.
     */
    fun createLine(fromPoint: Position, toPoint: Position): Shape {
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

    /**
     * Creates the points for the outline of a triangle. The triangle will begin at p1,
     * go through p2 and then p3 and then back to p1.
     */
    fun createTriangle(p1: Position, p2: Position, p3: Position): Shape {
        return createLine(p1, p2) + createLine(p2, p3) + createLine(p3, p1)
    }

    /**
     * Creates the points for a filled triangle. The triangle will begin at p1, go
     * through p2 and then p3 and then back to p1.
     */
    fun createFilledTriangle(p1: Position, p2: Position, p3: Position): Shape {
        // The algorithm described here is used
        // http://www-users.mat.uni.torun.pl/~wrona/3d_tutor/tri_fillers.html
        val points = arrayOf(p1, p2, p3)
        Arrays.sort(points, { o1, o2 -> if (o1.row < o2.row) -1 else if (o1.row == o2.row) 0 else 1 })
        var result: Shape = DefaultShape()

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
                result += createLine(
                        fromPoint = Position(startX.toInt(), startY.toInt()),
                        toPoint = Position(endX.toInt(), startY.toInt()))
                startY++
                startX += dx2
                endX += dx1
            }
            endX = points[1].column.toFloat()
            while (startY <= points[2].row) {
                result += createLine(
                        fromPoint = Position(startX.toInt(), startY.toInt()),
                        toPoint = Position(endX.toInt(), startY.toInt()))
                startY++
                startX += dx2
                endX += dx3
            }
        } else {
            while (startY <= points[1].row) {
                result += createLine(
                        fromPoint = Position(startX.toInt(), startY.toInt()),
                        toPoint = Position(endX.toInt(), startY.toInt()))
                startY++
                startX += dx1
                endX += dx2
            }
            startX = points[1].column.toFloat()
            startY = points[1].row.toFloat()
            while (startY <= points[2].row) {
                result += createLine(
                        fromPoint = Position(startX.toInt(), startY.toInt()),
                        toPoint = Position(endX.toInt(), startY.toInt()))
                startY++
                startX += dx3
                endX += dx2
            }
        }
        return result
    }

    /**
     * Creates the points for the outline of a rectangle.
     *
     * For example, calling [ShapeFactory.createRectangle] with size being the size of a terminal and top-left
     * value being the terminals top-left (0x0) corner will draw a border around the terminal.
     */
    fun createRectangle(topLeft: Position, size: Size): Shape {
        val topRight = topLeft.withRelativeColumn(size.columns - 1)
        val bottomRight = topRight.withRelativeRow(size.rows - 1)
        val bottomLeft = topLeft.withRelativeRow(size.rows - 1)
        return createLine(topLeft, topRight).plus(createLine(topRight, bottomRight))
                .plus(createLine(bottomRight, bottomLeft)).plus(createLine(bottomLeft, topLeft))
    }

    /**
     * Creates the points for a filled rectangle. The topLeft coordinate is inclusive.
     *
     * For example, calling [ShapeFactory.createFilledRectangle] with size being the size of the terminal
     * and top-left value being the terminals top-left (0x0) {} corner will fill the entire terminal
     * with this character.
     */
    fun createFilledRectangle(topLeft: Position, size: Size): Shape {
        return DefaultShape((0..size.rows - 1).flatMap { y ->
            (0..size.columns - 1).map { x ->
                Position(topLeft.column + x, topLeft.row + y)
            }
        }.toSet())
    }

    private fun createLine0(start: Position, deltaX: Int, deltaY: Int, leftToRight: Boolean): Set<Position> {
        val result = mutableSetOf<Position>()
        var dx = deltaX
        var (x, y) = start
        val deltaYx2 = deltaY * 2
        val deltaYx2MinusDeltaXx2 = deltaYx2 - dx * 2
        var errorTerm = deltaYx2 - dx
        result.add(Position(x, y))
        while (dx-- > 0) {
            if (errorTerm >= 0) {
                y++
                errorTerm += deltaYx2MinusDeltaXx2
            } else {
                errorTerm += deltaYx2
            }
            x += if (leftToRight) 1 else -1
            result.add(Position(x, y))
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
        result.add(Position(x, y))
        while (dy-- > 0) {
            if (errorTerm >= 0) {
                x += if (leftToRight) 1 else -1
                errorTerm += deltaXx2MinusDeltaYx2
            } else {
                errorTerm += deltaXx2
            }
            y++
            result.add(Position(x, y))
        }
        return result
    }
}