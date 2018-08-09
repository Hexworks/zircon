package org.hexworks.zircon.api.shape

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.shape.LineFactory.buildLine

object TriangleFactory : ShapeFactory<TriangleParameters> {

    override fun createShape(shapeParameters: TriangleParameters): Shape {
        val (p1, p2, p3) = shapeParameters
        return (buildLine(p1, p2) + buildLine(p2, p3) + buildLine(p3, p1)).offsetToDefaultPosition()
    }

    /**
     * Creates the points for the outline of a triangle. The outline will go through positions
     * `p1` to `p2` to `p3` and back to `p1` from there.
     *
     * *Note that** all resulting shapes will be offset to the top left (0x0) position!
     * @see [org.hexworks.zircon.api.shape.Shape.offsetToDefaultPosition] for more info!
     */
    fun buildTriangle(params: TriangleParameters) = createShape(params)

    /**
     * Creates the points for the outline of a triangle. The outline will go through positions
     * `p1` to `p2` to `p3` and back to `p1` from there.
     *
     * *Note that** all resulting shapes will be offset to the top left (0x0) position!
     * @see [org.hexworks.zircon.api.shape.Shape.offsetToDefaultPosition] for more info!
     */
    fun buildTriangle(p1: Position,
                      p2: Position,
                      p3: Position) = buildTriangle(TriangleParameters(p1, p2, p3))
}
