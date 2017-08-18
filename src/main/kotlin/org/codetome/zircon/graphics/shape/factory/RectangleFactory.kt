package org.codetome.zircon.graphics.shape.factory

import org.codetome.zircon.Position
import org.codetome.zircon.Size
import org.codetome.zircon.graphics.shape.ShapeFactory

object RectangleFactory : ShapeFactory<RectangleParameters> {

    override fun createShape(shapeParameters: RectangleParameters) = shapeParameters.let { (topLeft, size) ->
        val topRight = topLeft.withRelativeColumn(size.columns - 1)
        val bottomRight = topRight.withRelativeRow(size.rows - 1)
        val bottomLeft = topLeft.withRelativeRow(size.rows - 1)
        LineFactory.buildLine(topLeft, topRight)
                .plus(LineFactory.buildLine(topRight, bottomRight))
                .plus(LineFactory.buildLine(bottomRight, bottomLeft))
                .plus(LineFactory.buildLine(bottomLeft, topLeft))
    }

    /**
     * Creates the points for the outline of a rectangle.
     *
     * For example, calling this method with size being the size of a terminal and top-left
     * value being the terminals top-left (0x0) corner will create a shape which can be drawed
     * as a border for the terminal.
     */
    @JvmStatic
    fun buildRectangle(rectParams: RectangleParameters) = createShape(rectParams)

    /**
     * Creates the points for the outline of a rectangle.
     *
     * For example, calling this method with size being the size of a terminal and top-left
     * value being the terminals top-left (0x0) corner will create a shape which can be drawed
     * as a border for the terminal.
     */
    @JvmStatic
    fun buildRectangle(topLeft: Position, size: Size) = buildRectangle(RectangleParameters(topLeft, size))
}