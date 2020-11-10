package org.hexworks.zircon.api

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.shape.*
import kotlin.jvm.JvmStatic

/**
 * This object is a facade for the various [ShapeFactory] implementations.
 */
object Shapes {

    /**
     * Creates the points for a filled rectangle.
     *
     * For example, calling this method with size being the size of a grid and top-left
     * value being the terminals top-left (0x0) corner will create a shape which when drawn
     * will fill the whole grid.
     * **Note that** all resulting shapes will be offset to the top left (0x0) position!
     * @see [Shape.offsetToDefaultPosition] for more info!
     */
    @JvmStatic
    fun buildFilledRectangle(
            topLeft: Position,
            size: Size
    ) = FilledRectangleFactory.createShape(RectangleParameters(topLeft, size))

    /**
     * Creates the points for the outline of a triangle. The outline will go through positions
     * `p1` to `p2` to `p3` and back to `p1` from there.
     *
     * *Note that** all resulting shapes will be offset to the top left (0x0) position!
     * @see [Shape.offsetToDefaultPosition] for more info!
     */
    @JvmStatic
    fun buildTriangle(
            p1: Position,
            p2: Position,
            p3: Position
    ) = TriangleFactory.createShape(TriangleParameters(p1, p2, p3))

    /**
     * Creates the points for the outline of a rectangle.
     *
     * For example, calling this method with size being the size of a grid and top-left
     * value being the terminals top-left (0x0) corner will create a shape which when drawn
     * will outline the borders of the grid.
     * **Note that** all resulting shapes will be offset to the top left (0x0) position!
     * @see [Shape.offsetToDefaultPosition] for more info!
     */
    @JvmStatic
    fun buildRectangle(
            topLeft: Position,
            size: Size
    ) = RectangleFactory.createShape(RectangleParameters(topLeft, size))

    @JvmStatic
    fun buildLine(fromPoint: Position, toPoint: Position) = LineFactory.createShape(LineParameters(fromPoint, toPoint))

    /**
     * Creates the points for a filled triangle. The triangle will be delimited by
     * positions `p1` to `p2` to `p3` and back to `p1` from there.
     *
     * *Note that** all resulting shapes will be offset to the top left (0x0) position!
     * @see [Shape.offsetToDefaultPosition] for more info!
     */
    @JvmStatic
    fun buildFilledTriangle(
            p1: Position,
            p2: Position,
            p3: Position
    ) = FilledTriangleFactory.createShape(TriangleParameters(p1, p2, p3))
}
