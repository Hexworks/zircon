package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.internal.data.DefaultRect

/**
 * Represents a rectangular area with a given [Size] and [Position] on a 2D plane.
 */
interface Rect : Boundable {

    /**
     * Tells whether this [Rect] intersects with `boundable` or not.
     */
    override fun intersects(boundable: Boundable): Boolean

    /**
     * Tells whether `position` is within the bounds.
     */
    override fun containsPosition(position: Position): Boolean

    /**
     * Tells whether this bounds contains the `otherBounds`.
     * A [Rect] contains another if the other boundable's bounds
     * are within this one's. (If their bounds are the same it is considered
     * a containment).
     */
    override fun containsBoundable(boundable: Boundable): Boolean

    fun x() = position.x

    fun y() = position.y

    fun width() = size.width()

    fun height() = size.height()

    /**
     * The first component (for destructuring) is `x` position.
     */
    operator fun component1() = x()

    /**
     * The second component (for destructuring) is `y` position.
     */
    operator fun component2() = y()

    /**
     * The third component (for destructuring) is `width`.
     */
    operator fun component3() = width()

    /**
     * The fourth component (for destructuring) is `height`.
     */
    operator fun component4() = height()

    operator fun plus(rect: Rect) = Rect.create(
            position = position + rect.position,
            size = size + rect.size)

    operator fun minus(rect: Rect) = Rect.create(
            position = position - rect.position,
            size = size - rect.size)

    fun withPosition(position: Position) = create(position, size)

    fun withSize(size: Size) = create(position, size)

    fun withRelativePosition(position: Position) = create(position + position, size)

    fun withRelativeSize(size: Size) = create(position, size + size)

    companion object {

        fun create(position: Position = Position.defaultPosition(), size: Size): Rect {
            return DefaultRect(position, size)
        }
    }
}
