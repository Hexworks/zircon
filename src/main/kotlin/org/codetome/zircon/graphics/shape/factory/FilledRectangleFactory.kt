package org.codetome.zircon.graphics.shape.factory

import org.codetome.zircon.Position
import org.codetome.zircon.Size
import org.codetome.zircon.graphics.shape.DefaultShape
import org.codetome.zircon.graphics.shape.ShapeFactory

object FilledRectangleFactory : ShapeFactory<RectangleParameters> {

    override fun createShape(shapeParameters: RectangleParameters) = shapeParameters.let { (topLeft, size) ->
        DefaultShape((0 until size.rows).flatMap { y ->
            (0 until size.columns).map { x ->
                Position.of(topLeft.column + x, topLeft.row + y)
            }
        }.toSet()).offsetToDefaultPosition()
    }

    /**
     * Creates the points for a filled rectangle.
     *
     * For example, calling this method with size being the size of a terminal and top-left
     * value being the terminals top-left (0x0) corner will create a shape which when drawn
     * will fill the whole terminal.
     * **Note that** all resulting shapes will be offset to the top left (0x0) position!
     * @see [org.codetome.zircon.graphics.shape.Shape.offsetToDefaultPosition] for more info!
     */
    @JvmStatic
    fun buildFilledRectangle(rectParams: RectangleParameters) = createShape(rectParams)

    /**
     * Creates the points for a filled rectangle.
     *
     * For example, calling this method with size being the size of a terminal and top-left
     * value being the terminals top-left (0x0) corner will create a shape which when drawn
     * will fill the whole terminal.
     * **Note that** all resulting shapes will be offset to the top left (0x0) position!
     * @see [org.codetome.zircon.graphics.shape.Shape.offsetToDefaultPosition] for more info!
     */
    @JvmStatic
    fun buildFilledRectangle(topLeft: Position, size: Size)
            = buildFilledRectangle(RectangleParameters(topLeft, size))
}