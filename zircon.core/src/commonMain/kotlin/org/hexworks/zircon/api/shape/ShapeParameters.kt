package org.hexworks.zircon.api.shape

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size

sealed class ShapeParameters

data class LineParameters(
        val fromPoint: Position,
        val toPoint: Position
) : ShapeParameters()

data class TriangleParameters(
        val p1: Position,
        val p2: Position,
        val p3: Position
) : ShapeParameters()

data class RectangleParameters(
        val topLeft: Position,
        val size: Size
) : ShapeParameters()

data class EllipseParameters(
        val center: Position,
        val size: Size
) : ShapeParameters()
