package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.internal.data.DefaultRect

/**
 * Represents a rectangular area with a given [Size] and [Position] on a 2D plane.
 */
interface Rect : Boundable {

    val topLeft: Position
    val topCenter: Position
    val topRight: Position
    val rightCenter: Position
    val bottomRight: Position
    val bottomCenter: Position
    val bottomLeft: Position
    val leftCenter: Position
    val center: Position

    /**
     * The first component (for destructuring) is `x` position.
     */
    operator fun component1() = x

    /**
     * The second component (for destructuring) is `y` position.
     */
    operator fun component2() = y

    /**
     * The third component (for destructuring) is `width`.
     */
    operator fun component3() = width

    /**
     * The fourth component (for destructuring) is `height`.
     */
    operator fun component4() = height

    operator fun plus(rect: Rect): Rect

    operator fun minus(rect: Rect): Rect

    fun withPosition(position: Position): Rect

    fun withSize(size: Size): Rect

    fun withRelativePosition(position: Position): Rect

    fun withRelativeSize(size: Size): Rect

    fun splitHorizontal(splitAtX: Int): Pair<Rect, Rect>

    fun splitVertical(splitAtY: Int): Pair<Rect, Rect>

    companion object {

        /**
         * Creates a new [Rect] from the given [Position] and [Size].
         */
        fun create(position: Position = Position.defaultPosition(), size: Size): Rect {
            return DefaultRect(position, size)
        }
    }
}
