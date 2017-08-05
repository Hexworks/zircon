package org.codetome.zircon.graphics.shape

import org.codetome.zircon.Position

/**
 * A [Shape] is a set of [org.codetome.zircon.Position]s representing a geometric formation
 * (line, triangle, rectangle, box,  etc). A [Shape] is the most abstract representation of any graphic
 * object in Zircon and has no associated terminal, style, nor characters thus it is useful for
 * templating (like creating multiple versions of the same rectangle with different colors, shades
 * and characters).
 */
interface Shape : Collection<Position> {

    fun getPositions(): Set<Position>

    operator fun plus(shape: Shape): Shape {
        return DefaultShape(getPositions().plus(shape.getPositions()))
    }
}