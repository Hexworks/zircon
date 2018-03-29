package org.codetome.zircon.api.shape

import org.codetome.zircon.api.Position
import org.codetome.zircon.internal.graphics.DefaultShape
import org.codetome.zircon.api.graphics.Shape
import java.util.*

object FilledTriangleFactory : ShapeFactory<TriangleParameters> {

    override fun createShape(shapeParameters: TriangleParameters) = shapeParameters.let { (p1, p2, p3) ->

        // The algorithm described here is used
        // http://www-users.mat.uni.torun.pl/~wrona/3d_tutor/tri_fillers.html
        val points = arrayOf(p1, p2, p3)
        Arrays.sort(points, { o1, o2 -> if (o1.y < o2.y) -1 else if (o1.y == o2.y) 0 else 1 })
        var result: Shape = DefaultShape()

        val dx1: Float
        val dx2: Float
        val dx3: Float
        if (points[1].y - points[0].y > 0) {
            dx1 = (points[1].x - points[0].x).toFloat() / (points[1].y - points[0].y).toFloat()
        } else {
            dx1 = 0f
        }
        if (points[2].y - points[0].y > 0) {
            dx2 = (points[2].x - points[0].x).toFloat() / (points[2].y - points[0].y).toFloat()
        } else {
            dx2 = 0f
        }
        if (points[2].y - points[1].y > 0) {
            dx3 = (points[2].x - points[1].x).toFloat() / (points[2].y - points[1].y).toFloat()
        } else {
            dx3 = 0f
        }

        var startX: Float
        var startY: Float
        var endX: Float
        endX = points[0].x.toFloat()
        startX = endX
        startY = points[0].y.toFloat()
        if (dx1 > dx2) {
            while (startY <= points[1].y) {
                result += LineFactory.buildLine(
                        fromPoint = Position.of(startX.toInt(), startY.toInt()),
                        toPoint = Position.of(endX.toInt(), startY.toInt()))
                startY++
                startX += dx2
                endX += dx1
            }
            endX = points[1].x.toFloat()
            while (startY <= points[2].y) {
                result += LineFactory.buildLine(
                        fromPoint = Position.of(startX.toInt(), startY.toInt()),
                        toPoint = Position.of(endX.toInt(), startY.toInt()))
                startY++
                startX += dx2
                endX += dx3
            }
        } else {
            while (startY <= points[1].y) {
                result += LineFactory.buildLine(
                        fromPoint = Position.of(startX.toInt(), startY.toInt()),
                        toPoint = Position.of(endX.toInt(), startY.toInt()))
                startY++
                startX += dx1
                endX += dx2
            }
            startX = points[1].x.toFloat()
            startY = points[1].y.toFloat()
            while (startY <= points[2].y) {
                result += LineFactory.buildLine(
                        fromPoint = Position.of(startX.toInt(), startY.toInt()),
                        toPoint = Position.of(endX.toInt(), startY.toInt()))
                startY++
                startX += dx3
                endX += dx2
            }
        }
        result.offsetToDefaultPosition()
    }

    /**
     * Creates the points for a filled triangle. The triangle will be delimited by
     * positions `p1` to `p2` to `p3` and back to `p1` from there.
     *
     * *Note that** all resulting shapes will be offset to the top left (0x0) position!
     * @see [org.codetome.zircon.api.graphics.Shape.offsetToDefaultPosition] for more info!
     */
    @JvmStatic
    fun buildFilledTriangle(params: TriangleParameters) = createShape(params)

    /**
     * Creates the points for a filled triangle. The triangle will be delimited by
     * positions `p1` to `p2` to `p3` and back to `p1` from there.
     *
     * *Note that** all resulting shapes will be offset to the top left (0x0) position!
     * @see [org.codetome.zircon.api.graphics.Shape.offsetToDefaultPosition] for more info!
     */
    @JvmStatic
    fun buildFilledTriangle(p1: Position,
                            p2: Position,
                            p3: Position) = buildFilledTriangle(TriangleParameters(p1, p2, p3))
}
