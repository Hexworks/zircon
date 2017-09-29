package org.codetome.zircon.api.shape

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size

object RectangleFactory : ShapeFactory<RectangleParameters> {

    override fun createShape(shapeParameters: RectangleParameters) = shapeParameters.let { (topLeft, size) ->
        val topRight = topLeft.withRelativeColumn(size.columns - 1)
        val bottomRight = topRight.withRelativeRow(size.rows - 1)
        val bottomLeft = topLeft.withRelativeRow(size.rows - 1)
        LineFactory.buildLine(topLeft, topRight)
                .plus(LineFactory.buildLine(topRight, bottomRight))
                .plus(LineFactory.buildLine(bottomRight, bottomLeft))
                .plus(LineFactory.buildLine(bottomLeft, topLeft))
                .offsetToDefaultPosition()
    }

    /**
     * Creates the points for the outline of a rectangle.
     *
     * For example, calling this method with size being the size of a terminal and top-left
     * value being the terminals top-left (0x0) corner will create a shape which when drawn
     * will outline the borders of the terminal.
     * **Note that** all resulting shapes will be offset to the top left (0x0) position!
     * @see [org.codetome.zircon.api.graphics.Shape.offsetToDefaultPosition] for more info!
     */
    @JvmStatic
    fun buildRectangle(rectParams: RectangleParameters) = createShape(rectParams)

    /**
     * Creates the points for the outline of a rectangle.
     *
     * For example, calling this method with size being the size of a terminal and top-left
     * value being the terminals top-left (0x0) corner will create a shape which when drawn
     * will outline the borders of the terminal.
     * **Note that** all resulting shapes will be offset to the top left (0x0) position!
     * @see [org.codetome.zircon.api.graphics.Shape.offsetToDefaultPosition] for more info!
     */
    @JvmStatic
    fun buildRectangle(topLeft: Position, size: Size) = buildRectangle(RectangleParameters(topLeft, size))
}