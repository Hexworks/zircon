package org.codetome.zircon.api.behavior

import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size

/**
 * Represents an object which has bounds in 2D space. A [Boundable] object can provide useful information
 * about its geometry relating to other [Boundable]s (like intersection).
 */
interface Boundable : Positionable {

    /**
     * Returns the [Size] of this [Boundable].
     */
    fun getBoundableSize(): Size

    /**
     * Tells whether this [Boundable] intersects the other `boundable` or not.
     */
    fun intersects(boundable: Boundable): Boolean

    /**
     * Tells whether `position` is within this boundable's bounds.
     */
    fun containsPosition(position: Position): Boolean

    /**
     * Tells whether this boundable contains the other `boundable`.
     * A [Boundable] contains another if the other boundable's bounds
     * are within this one's. (If their bounds are the same it is considered
     * a containment).
     */
    fun containsBoundable(boundable: Boundable): Boolean
}
