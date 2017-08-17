package org.codetome.zircon.graphics.shape.test

import org.codetome.zircon.Position
import org.codetome.zircon.graphics.shape.Shape
import org.codetome.zircon.graphics.shape.test.LineBuilder.buildLine

object TriangleBuilder : ShapeBuilder<TriangleParameters> {

    override fun buildShape(shapeParameters: TriangleParameters): Shape {
        val (p1, p2, p3) = shapeParameters
        return buildLine(p1, p2) + buildLine(p2, p3) + buildLine(p3, p1)
    }

    @JvmStatic
    fun buildTriangle(params: TriangleParameters) = buildShape(params)

    @JvmStatic
    fun buildTriangle(p1: Position,
                      p2: Position,
                      p3: Position) = buildTriangle(TriangleParameters(p1, p2, p3))
}