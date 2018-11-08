@file:Suppress("unused", "ReplaceSingleLineLet")

package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.data.impl.GridPosition
import org.hexworks.zircon.api.data.impl.PixelPosition
import org.hexworks.zircon.api.resource.TilesetResource

/**
 * Represents a coordinate on a 2D plane.
 */
interface Position : Comparable<Position> {

    val x: Int
    val y: Int

    operator fun component1() = x

    operator fun component2() = y

    /**
     * Returns a new [Position] which is the sum of `x` and `y` in both [Position]s.
     * so `Position(x = 1, y = 1).plus(Position(x = 2, y = 2))` will be
     * `Position(x = 3, y = 3)`.
     */
    operator fun plus(other: Position): Position

    /**
     * Returns a new [Position] which is the difference of `x` and `y`  both [Position]s.
     * so `Position(x = 3, y = 3).minus(Position(x = 2, y = 2))` will be
     * `Position(x = 1, y = 1)`.
     */
    operator fun minus(other: Position): Position

    /**
     * Turns this [Position] to a [PixelPosition]. Has no effect if
     * `this` object is a [PixelPosition].
     */
    fun toPixelPosition(tileset: TilesetResource): PixelPosition

    /**
     * Creates a new [Position] object representing a position with the same y index as this but with a
     * supplied x index.
     */
    fun withX(x: Int): Position

    /**
     * Creates a new [Position] object representing a position with the same y index as this but with a
     * supplied y index.
     */
    fun withY(y: Int): Position

    /**
     * Creates a new [Position] object representing a position on the same y, but with a x offset by a
     * supplied value. Calling this method with delta 0 will returnThis this, calling it with a positive
     * delta will returnThis a grid position <code>delta</code> number of x to the right and
     * for negative numbers the same to the left.
     */
    fun withRelativeX(delta: Int): Position

    /**
     * Creates a new [Position] object representing a position on the same x, but with a y offset by a
     * supplied value. Calling this method with delta 0 will returnThis this, calling it with a positive delta
     * will returnThis a grid position <code>delta</code> number of y to the down and for negative
     * numbers the same up.
     */
    fun withRelativeY(delta: Int): Position

    /**
     * Creates a new [Position] object that is translated by an amount of x and y specified by another
     * [Position]. Same as calling `withRelativeY(translate.getYLength()).withRelativeX(translate.getXLength())`.
     */
    fun withRelative(translate: Position): Position

    /**
     * Transforms this [Position] to a [Size] so if
     * this position is Position(x=2, y=3) it will become
     * Size(x=2, y=3).
     */
    fun toSize(): Size

    /**
     * Creates a [Position] which is relative to the top of the given [Component].
     * The x coordinate is used to shift right
     * The y coordinate is used to shift up
     */
    fun relativeToTopOf(component: Component): Position

    /**
     * Creates a [Position] which is relative to the right of the given [Component].
     * The x coordinate is used to shift right
     * The y coordinate is used to shift down
     */
    fun relativeToRightOf(component: Component): Position

    /**
     * Creates a [Position] which is relative to the bottom of the given [Component].
     * The x coordinate is used to shift right
     * The y coordinate is used to shift down
     */
    fun relativeToBottomOf(component: Component): Position

    /**
     * Creates a [Position] which is relative to the top left of the given [Component].
     * The x coordinate is used to shift left
     * The y coordinate is used to shift down
     */
    fun relativeToLeftOf(component: Component): Position

    /**
     * Tells whether this [Position] is `UNKNOWN`.
     */
    fun isUnknown(): Boolean

    /**
     * Tells whether this [Position] is not `UNKNOWN`.
     */
    fun isNotUnknown(): Boolean

    /**
     * Tells whether this [Position] has a negative component (x or y) or not.
     */
    fun hasNegativeComponent(): Boolean

    companion object {

        /**
         * Constant for the top-left corner (0x0)
         */
        fun topLeftCorner() = TOP_LEFT_CORNER

        /**
         * Constant for the 1x1 position (one offset in both directions from top-left)
         */
        fun offset1x1() = OFFSET_1X1

        /**
         * Constant for the 0x0 position.
         */
        fun zero() = DEFAULT_POSITION

        /**
         * This position can be considered as the default (0x0).
         */
        fun defaultPosition() = DEFAULT_POSITION

        /**
         * Used in place of a possible null value. Means that the position is unknown (cursor for example).
         */
        fun unknown() = UNKNOWN

        /**
         * Creates a new [Position] using the given `x` and `y` values.
         */
        fun create(x: Int, y: Int): Position {
            return GridPosition(x, y)
        }

        /**
         * Returns the top left position of the given [Component].
         */
        fun topLeftOf(component: Component) = component.position

        /**
         * Returns the top right position of the given [Component].
         */
        fun topRightOf(component: Component) = component.position.withRelativeX(component.width)

        /**
         * Returns the bottom left position of the given [Component].
         */
        fun bottomLeftOf(component: Component) = component.position.withRelativeY(component.height)

        /**
         * Returns the bottom right position of the given [Component].
         */
        fun bottomRightOf(component: Component) = component.position.withRelative(component.size.toPosition())

        private val TOP_LEFT_CORNER = create(0, 0)
        private val OFFSET_1X1 = create(1, 1)
        private val DEFAULT_POSITION = TOP_LEFT_CORNER
        private val UNKNOWN = create(Int.MAX_VALUE, Int.MAX_VALUE)
    }
}
