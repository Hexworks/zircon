package org.hexworks.zircon.api.behavior

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Rect
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.behavior.impl.DefaultMovable
import kotlin.jvm.JvmStatic

/**
 * Represents an object which has bounds and a position in 2D space. A [Boundable] object
 * can provide useful information about its geometry relating to other [Boundable]s
 * (like intersection).
 */
@Suppress("JVM_STATIC_IN_INTERFACE_1_6")
interface Boundable : HasSize {

    /**
     * A [Rect] contains the coordinates and the size of a [Boundable].
     */
    val rect: Rect

    /**
     * Shorthand for [Rect.position]
     */
    val position: Position
        get() = rect.position

    /**
     * Shorthand for [Rect.x]
     */
    val x: Int
        get() = rect.x

    /**
     * Shorthand for [Rect.y]
     */
    val y: Int
        get() = rect.y

    /**
     * Tells whether this [Boundable] intersects with the other [boundable].
     */
    infix fun intersects(boundable: Boundable): Boolean

    /**
     * Tells whether [position] is within this boundable's bounds.
     */
    infix fun containsPosition(position: Position): Boolean

    /**
     * Tells whether this boundable contains the other [boundable].
     * A [Boundable] contains another if the other boundable's bounds
     * are within this one's. (If their bounds are the same it is considered
     * a containment).
     */
    infix fun containsBoundable(boundable: Boundable): Boolean

    companion object {

        @JvmStatic
        fun create(position: Position = Position.defaultPosition(), size: Size): Boundable {
            return DefaultMovable(size, position)
        }
    }
}
