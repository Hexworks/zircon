package org.hexworks.zircon.api.behavior

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Rect
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.behavior.impl.DefaultMovable

/**
 * Represents an object which has bounds and a position in 2D space.
 * A [Boundable] object can provide useful information
 * about its geometry relating to other [Boundable]s (like intersection).
 */
interface Boundable {

    val position: Position
    val size: Size
    val rect: Rect
    val x: Int
    val y: Int
    val width: Int
    val height: Int

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

    companion object {

        fun create(position: Position = Position.defaultPosition(), size: Size): Boundable {
            return DefaultMovable(size, position)
        }
    }
}
