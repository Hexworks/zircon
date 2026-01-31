package org.hexworks.zircon.api.shape

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.shape.DefaultShape
import kotlin.math.abs

object EllipseFactory : ShapeFactory<EllipseParameters> {

    override fun createShape(shapeParameters: EllipseParameters): Shape {
        val xc = shapeParameters.center.x
        val yc = shapeParameters.center.y
        val width = shapeParameters.size.width
        val height = shapeParameters.size.height
        val result = mutableSetOf<Position>()

        // Bresenham's algorithm for ellipse is taken from:
        // https://sites.google.com/site/ruslancray/lab/projects/bresenhamscircleellipsedrawingalgorithm/bresenham-s-circle-ellipse-drawing-algorithm

        val a2 = width * width
        val b2 = height * height
        val fa2 = 4 * a2
        val fb2 = 4 * b2

        // First half.
        var x = 0
        var y = height
        var sigma = 2 * b2 + a2 * (1 - 2 * height)
        while (b2 * x <= a2 * y) {
            result.add(Position.create(xc + x, yc + y))
            result.add(Position.create(xc - x, yc + y))
            result.add(Position.create(xc + x, yc - y))
            result.add(Position.create(xc - x, yc - y))
            if (sigma >= 0) {
                sigma += fa2 * (1 - y)
                y--
            }
            sigma += b2 * ((4 * x) + 6)
            x++
        }

        // Second half.
        x = width
        y = 0
        sigma = 2 * a2 + b2 * (1 - 2 * width)
        while (a2 * y <= b2 * x) {
            result.add(Position.create(xc + x, yc + y))
            result.add(Position.create(xc - x, yc + y))
            result.add(Position.create(xc + x, yc - y))
            result.add(Position.create(xc - x, yc - y))
            if (sigma >= 0) {
                sigma += fb2 * (1 - x)
                x--
            }
            sigma += a2 * ((4 * y) + 6)
            y++
        }

        return DefaultShape(result)
    }

    fun buildEllipse(ellipseParams: EllipseParameters) = createShape(ellipseParams)

    fun buildEllipse(fromPosition: Position, toPosition: Position): Shape {
        if (fromPosition == toPosition) {
            // Need to return here otherwise blows up.
            return DefaultShape(mutableSetOf())
        }
        val delta = fromPosition.minus(toPosition)
        return createShape(
            EllipseParameters(
                center = fromPosition,
                size = Size.create(abs(delta.x), abs(delta.y))
            )
        )
    }
}
