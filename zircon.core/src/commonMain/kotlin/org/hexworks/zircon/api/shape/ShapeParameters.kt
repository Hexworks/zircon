package org.hexworks.zircon.api.shape

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size

public sealed class ShapeParameters

public data class LineParameters(
    val fromPoint: Position,
    val toPoint: Position
) : ShapeParameters()

public data class TriangleParameters(
    val p1: Position,
    val p2: Position,
    val p3: Position
) : ShapeParameters()

public data class RectangleParameters(
    val topLeft: Position,
    val size: Size
) : ShapeParameters()

public data class EllipseParameters(
    val center: Position,
    val size: Size
) : ShapeParameters()
