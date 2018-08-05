package org.codetome.zircon.jvm.api.interop

import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.shape.*

object Shapes {

    /**
     * Creates the points for a filled rectangle.
     *
     * For example, calling this method with size being the size of a grid and top-left
     * value being the terminals top-left (0x0) corner will create a shape which when drawn
     * will fill the whole grid.
     * **Note that** all resulting shapes will be offset to the top left (0x0) position!
     * @see [org.codetome.zircon.api.graphics.Shape.offsetToDefaultPosition] for more info!
     */
    @JvmStatic
    fun buildFilledRectangle(rectParams: RectangleParameters) = FilledRectangleFactory.createShape(rectParams)

    /**
     * Creates the points for a filled rectangle.
     *
     * For example, calling this method with size being the size of a grid and top-left
     * value being the terminals top-left (0x0) corner will create a shape which when drawn
     * will fill the whole grid.
     * **Note that** all resulting shapes will be offset to the top left (0x0) position!
     * @see [org.codetome.zircon.api.graphics.Shape.offsetToDefaultPosition] for more info!
     */
    @JvmStatic
    fun buildFilledRectangle(topLeft: Position, size: Size) = buildFilledRectangle(RectangleParameters(topLeft, size))

    /**
     * Creates the points for the outline of a triangle. The outline will go through positions
     * `p1` to `p2` to `p3` and back to `p1` from there.
     *
     * *Note that** all resulting shapes will be offset to the top left (0x0) position!
     * @see [org.codetome.zircon.api.graphics.Shape.offsetToDefaultPosition] for more info!
     */
    @JvmStatic
    fun buildTriangle(params: TriangleParameters) = TriangleFactory.createShape(params)

    /**
     * Creates the points for the outline of a triangle. The outline will go through positions
     * `p1` to `p2` to `p3` and back to `p1` from there.
     *
     * *Note that** all resulting shapes will be offset to the top left (0x0) position!
     * @see [org.codetome.zircon.api.graphics.Shape.offsetToDefaultPosition] for more info!
     */
    @JvmStatic
    fun buildTriangle(p1: Position,
                      p2: Position,
                      p3: Position) = buildTriangle(TriangleParameters(p1, p2, p3))

    /**
     * Creates the points for the outline of a rectangle.
     *
     * For example, calling this method with size being the size of a grid and top-left
     * value being the terminals top-left (0x0) corner will create a shape which when drawn
     * will outline the borders of the grid.
     * **Note that** all resulting shapes will be offset to the top left (0x0) position!
     * @see [org.codetome.zircon.api.graphics.Shape.offsetToDefaultPosition] for more info!
     */
    @JvmStatic
    fun buildRectangle(rectParams: RectangleParameters) = RectangleFactory.createShape(rectParams)

    /**
     * Creates the points for the outline of a rectangle.
     *
     * For example, calling this method with size being the size of a grid and top-left
     * value being the terminals top-left (0x0) corner will create a shape which when drawn
     * will outline the borders of the grid.
     * **Note that** all resulting shapes will be offset to the top left (0x0) position!
     * @see [org.codetome.zircon.api.graphics.Shape.offsetToDefaultPosition] for more info!
     */
    @JvmStatic
    fun buildRectangle(topLeft: Position, size: Size) = buildRectangle(RectangleParameters(topLeft, size))

    @JvmStatic
    fun buildLine(lineParams: LineParameters) = LineFactory.createShape(lineParams)

    @JvmStatic
    fun buildLine(fromPoint: Position, toPoint: Position) = buildLine(LineParameters(fromPoint, toPoint))

    /**
     * Creates the points for a filled triangle. The triangle will be delimited by
     * positions `p1` to `p2` to `p3` and back to `p1` from there.
     *
     * *Note that** all resulting shapes will be offset to the top left (0x0) position!
     * @see [org.codetome.zircon.api.graphics.Shape.offsetToDefaultPosition] for more info!
     */
    @JvmStatic
    fun buildFilledTriangle(params: TriangleParameters) = FilledTriangleFactory.createShape(params)

    /**
     * Creates the points for a filled triangle. The triangle will be delimited by
     * positions `p1` to `p2` to `p3` and back to `p1` from there.
     *
     * *Note that** all resulting shapes will be offset to the top left (0x0) position!
     * @see [org.codetome.zircon.api.graphics.Shape.offsetToDefaultPosition] for more info!
     */
    @JvmStatic
    fun buildFilledTriangle(p1: Position,
                            p2: Position,
                            p3: Position) = buildFilledTriangle(TriangleParameters(p1, p2, p3))
}
