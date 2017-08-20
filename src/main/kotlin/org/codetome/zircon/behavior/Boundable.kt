package org.codetome.zircon.behavior

import org.codetome.zircon.Position
import org.codetome.zircon.Size

/**
 * Represents an object which has bounds in 2d space. A [Boundable] object can provide useful information
 * about its geometry relating to other [Boundable]s (like intersection).
 */
interface Boundable : Positionable {

    /**
     * Returns the [Size] of this [Boundable].
     */
    fun getBoundableSize(): Size

    /**
     * Tells whether this [Boundable] intersects the other <code>boundable</code> or not.
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