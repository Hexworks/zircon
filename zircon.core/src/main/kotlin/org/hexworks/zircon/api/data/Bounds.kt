package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.behavior.Positionable
import org.hexworks.zircon.internal.data.DefaultBounds

/**
 * Represents a snapshot of the state of a [Boundable]
 * eg: its [Position] and its [Size]. While [Boundable] represents a behavior of an
 * object [Bounds] represents a value object which encapsulates is state.
 */
interface Bounds {

    /**
     * Returns the position of this [Bounds]. Default is (0, 0).
     * The position of a [Positionable] is its position relative to the
     * gui window's top left corner. An offset of (0, 0) denotes
     * that corner.
     * Only override the default if it is applicable in your context
     * (in [org.hexworks.zircon.api.graphics.Layer] for example).
     */
    fun position(): Position

    /**
     * Returns the [Size] of this [Bounds].
     */
    fun size(): Size

    fun x() = position().x

    fun y() = position().y

    fun width() = size().width()

    fun height() = size().height()

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

    /**
     * Tells whether `position` is within the bounds.
     */
    fun containsPosition(position: Position): Boolean

    /**
     * Tells whether this [Bounds] intersects the other `bounds` or not.
     */
    fun intersects(otherBounds: Bounds): Boolean

    /**
     * Tells whether this bounds contains the `otherBounds`.
     * A [Bounds] contains another if the other boundable's bounds
     * are within this one's. (If their bounds are the same it is considered
     * a containment).
     */
    fun containsBounds(otherBounds: Bounds): Boolean

    companion object {

        fun create(position: Position, size: Size): Bounds {
            return DefaultBounds(position, size)
        }
    }
}
