package org.codetome.zircon.behavior

import org.codetome.zircon.Position
import org.codetome.zircon.terminal.Size
import java.util.*

/**
 * Represents an object which has bounds in 2d space. A [Boundable] object can provide useful information
 * about its geometry relating to other [Boundable]s (like intersection).
 */
interface Boundable {

    /**
     * Returns the [Size] of this [Boundable].
     */
    fun getBoundableSize(): Size

    /**
     * Tells whether this [Boundable] intersects the other <code>boundable</code> or not.
     */
    fun intersects(boundable: Boundable): Boolean

    fun containsPosition(position: Position): Boolean

    fun containsBoundable(boundable: Boundable): Boolean
}