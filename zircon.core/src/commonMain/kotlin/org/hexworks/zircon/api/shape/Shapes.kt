package org.hexworks.zircon.api.shape

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size

/** A line from [from] to [to], both ends included. */
public fun line(from: Position, to: Position): Shape =
    LineFactory.createShape(LineParameters(from, to))

/** The outline of a rectangle. */
public fun rectangle(topLeft: Position, size: Size): Shape =
    RectangleFactory.createShape(RectangleParameters(topLeft, size))

/** A rectangle including its interior. */
public fun filledRectangle(topLeft: Position, size: Size): Shape =
    FilledRectangleFactory.createShape(RectangleParameters(topLeft, size))

/** The outline of a triangle. */
public fun triangle(p1: Position, p2: Position, p3: Position): Shape =
    TriangleFactory.createShape(TriangleParameters(p1, p2, p3))

/** A triangle including its interior. */
public fun filledTriangle(p1: Position, p2: Position, p3: Position): Shape =
    FilledTriangleFactory.createShape(TriangleParameters(p1, p2, p3))
