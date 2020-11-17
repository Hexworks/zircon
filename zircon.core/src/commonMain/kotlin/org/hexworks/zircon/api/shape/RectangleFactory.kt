package org.hexworks.zircon.api.shape

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size

object RectangleFactory : ShapeFactory<RectangleParameters> {

    override fun createShape(shapeParameters: RectangleParameters) = shapeParameters.let { (topLeft, size) ->
        val topRight = topLeft.withRelativeX(size.width - 1)
        val bottomRight = topRight.withRelativeY(size.height - 1)
        val bottomLeft = topLeft.withRelativeY(size.height - 1)
        LineFactory.buildLine(topLeft, topRight)
                .plus(LineFactory.buildLine(topRight, bottomRight))
                .plus(LineFactory.buildLine(bottomRight, bottomLeft))
                .plus(LineFactory.buildLine(bottomLeft, topLeft))
        // TODO: document that offsetToDefaultPositionWasRemoved
    }

    /**
     * Creates the points for the outline of a rectangle.
     *
     * For example, calling this method with size being the size of a grid and top-left
     * value being the terminals top-left (0x0) corner will create a shape which when drawn
     * will outline the borders of the grid.
     * **Note that** all resulting shapes will be offset to the top left (0x0) position!
     * @see [org.hexworks.zircon.api.shape.Shape.offsetToDefaultPosition] for more info!
     */
    fun buildRectangle(rectParams: RectangleParameters) = createShape(rectParams)

    /**
     * Creates the points for the outline of a rectangle.
     *
     * For example, calling this method with size being the size of a grid and top-left
     * value being the terminals top-left (0x0) corner will create a shape which when drawn
     * will outline the borders of the grid.
     * **Note that** all resulting shapes will be offset to the top left (0x0) position!
     * @see [org.hexworks.zircon.api.shape.Shape.offsetToDefaultPosition] for more info!
     */
    fun buildRectangle(topLeft: Position, size: Size) = buildRectangle(RectangleParameters(topLeft, size))
}
