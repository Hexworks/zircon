package org.codetome.zircon.behavior

import org.codetome.zircon.Position
import org.codetome.zircon.terminal.Size
import java.util.*

/**
 * Represents an object which has bounds in 2d space. A [Boundable] object can provide useful information
 * about its geometry relating to other [Boundable] (like intersection).
 */
interface Boundable {

    fun getPosition(): Position

    fun getSize(): Size

    fun intersects(boundable: Boundable): Boolean

    fun calculateIntersectionForBoundable(boundable: Boundable): Optional<Boundable>

    fun containsPosition(position: Position): Boolean

    fun containsBoundable(boundable: Boundable): Boolean
}