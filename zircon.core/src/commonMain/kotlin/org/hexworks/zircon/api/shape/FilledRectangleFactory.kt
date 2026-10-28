package org.hexworks.zircon.api.shape

import org.hexworks.zircon.internal.shape.DefaultShape
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size

public object FilledRectangleFactory : ShapeFactory<RectangleParameters> {

    override fun createShape(shapeParameters: RectangleParameters): Shape = shapeParameters.let { (topLeft, size) ->
        DefaultShape((0 until size.height).flatMap { y ->
            (0 until size.width).map { x ->
                Position(topLeft.x + x, topLeft.y + y)
            }
        }.toSet())
    }

    /**
     * Creates the points for a filled rectangle.
     *
     * For example, calling this method with size being the size of a grid and top-left
     * value being the terminals top-left (0x0) corner will create a shape which when drawn
     * will fill the whole grid.
     * **Note that** all resulting shapes will be offset to the top left (0x0) position!
     * @see [org.hexworks.zircon.api.shape.Shape.offsetToDefaultPosition] for more info!
     */
    public fun buildFilledRectangle(rectParams: RectangleParameters): Shape = createShape(rectParams)

    /**
     * Creates the points for a filled rectangle.
     *
     * For example, calling this method with size being the size of a grid and top-left
     * value being the terminals top-left (0x0) corner will create a shape which when drawn
     * will fill the whole grid.
     * **Note that** all resulting shapes will be offset to the top left (0x0) position!
     * @see [org.hexworks.zircon.api.shape.Shape.offsetToDefaultPosition] for more info!
     */
    public fun buildFilledRectangle(topLeft: Position, size: Size): Shape = buildFilledRectangle(RectangleParameters(topLeft, size))
}
