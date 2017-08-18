package org.codetome.zircon.graphics.shape.factory

import org.codetome.zircon.Position
import org.codetome.zircon.Size
import org.codetome.zircon.graphics.shape.DefaultShape
import org.codetome.zircon.graphics.shape.ShapeFactory

object FilledRectangleFactory : ShapeFactory<RectangleParameters> {

    override fun createShape(shapeParameters: RectangleParameters) = shapeParameters.let { (topLeft, size) ->
        DefaultShape((0..size.rows - 1).flatMap { y ->
            (0..size.columns - 1).map { x ->
                Position.of(topLeft.column + x, topLeft.row + y)
            }
        }.toSet())
    }

    /**
     * Creates the points for the outline of a rectangle.
     *
     * For example, calling this method with size being the size of a terminal and top-left
     * value being the terminals top-left (0x0) corner will create a shape which when drawn
     * will fill the whole terminal.
     */
    @JvmStatic
    fun buildFilledRectangle(rectParams: RectangleParameters) = createShape(rectParams)

    /**
     * Creates the points for the outline of a rectangle.
     *
     * For example, calling this method with size being the size of a terminal and top-left
     * value being the terminals top-left (0x0) corner will create a shape which when drawn
     * will fill the whole terminal.
     */
    @JvmStatic
    fun buildFilledRectangle(topLeft: Position, size: Size)
            = buildFilledRectangle(RectangleParameters(topLeft, size))
}