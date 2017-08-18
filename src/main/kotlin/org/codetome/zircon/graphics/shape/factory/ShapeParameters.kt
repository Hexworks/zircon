package org.codetome.zircon.graphics.shape.factory

import org.codetome.zircon.Position
import org.codetome.zircon.Size

sealed class ShapeParameters

data class LineParameters(val fromPoint: Position,
                          val toPoint: Position) : ShapeParameters()

data class TriangleParameters(val p1: Position,
                              val p2: Position,
                              val p3: Position): ShapeParameters()

data class RectangleParameters(val topLeft: Position,
                               val size: Size): ShapeParameters()