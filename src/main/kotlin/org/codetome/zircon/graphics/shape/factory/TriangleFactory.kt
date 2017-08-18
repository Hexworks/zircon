package org.codetome.zircon.graphics.shape.factory

import org.codetome.zircon.Position
import org.codetome.zircon.graphics.shape.Shape
import org.codetome.zircon.graphics.shape.ShapeFactory
import org.codetome.zircon.graphics.shape.factory.LineFactory.buildLine

object TriangleFactory : ShapeFactory<TriangleParameters> {

    override fun createShape(shapeParameters: TriangleParameters): Shape {
        val (p1, p2, p3) = shapeParameters
        return buildLine(p1, p2) + buildLine(p2, p3) + buildLine(p3, p1)
    }

    @JvmStatic
    fun buildTriangle(params: TriangleParameters) = createShape(params)

    @JvmStatic
    fun buildTriangle(p1: Position,
                      p2: Position,
                      p3: Position) = buildTriangle(TriangleParameters(p1, p2, p3))
}