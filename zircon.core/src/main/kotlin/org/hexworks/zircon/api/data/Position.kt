@file:Suppress("unused", "ReplaceSingleLineLet")

package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.tileset.Tileset

interface Position : Comparable<Position> {

    val x: Int
    val y: Int

    operator fun component1() = x

    operator fun component2() = y

    fun toAbsolutePosition(tileset: Tileset<out Any>): AbsolutePosition

    override fun compareTo(other: Position): Int {
        checkType(this, other)
        return when {
            y > other.y -> 1
            y == other.y && x > other.x -> 1
            y == other.y && x == other.x -> 0
            else -> -1
        }
    }

    /**
     * Returns a new [Position] which is the sum of `x` and `y` in both [Position]s.
     * so `Position(x = 1, y = 1).plus(Position(x = 2, y = 2))` will be
     * `Position(x = 3, y = 3)`.
     */
    operator fun plus(other: Position): Position {
        checkType(this, other)
        if (other == topLeftCorner()) {
            return this
        }
        return when (this) {
            is AbsolutePosition -> AbsolutePosition(
                    x = x + other.x,
                    y = y + other.y)
            is GridPosition -> GridPosition(
                    x = x + other.x,
                    y = y + other.y)
            else -> throw UnsupportedOperationException("Can't add unknown Position type")
        }
    }

    /**
     * Returns a new [Position] which is the difference of `x` and `y`  both [Position]s.
     * so `Position(x = 3, y = 3).minus(Position(x = 2, y = 2))` will be
     * `Position(x = 1, y = 1)`.
     */
    operator fun minus(other: Position): Position {
        checkType(this, other)
        if (other == topLeftCorner()) {
            return this
        }
        return when (this) {
            is AbsolutePosition -> AbsolutePosition(
                    x = x - other.x,
                    y = y - other.y)
            is GridPosition -> GridPosition(
                    x = x - other.x,
                    y = y - other.y)
            else -> throw UnsupportedOperationException("Can't subtract unknown Position type")
        }
    }

    /**
     * Creates a new [Position] object representing a position with the same y index as this but with a
     * supplied x index.
     */
    fun withX(x: Int): Position {
        if (this.x == x) {
            return this
        }
        return if (x == 0 && y == 0) {
            DEFAULT_POSITION
        } else create(x, y, this)
    }

    /**
     * Creates a new [Position] object representing a position with the same y index as this but with a
     * supplied y index.
     */
    fun withY(y: Int): Position {
        if (this.y == y) {
            return this
        }
        return if (x == 0 && y == 0) {
            DEFAULT_POSITION
        } else create(x, y, this)
    }

    /**
     * Creates a new [Position] object representing a position on the same y, but with a x offset by a
     * supplied value. Calling this method with delta 0 will return this, calling it with a positive
     * delta will return a grid position <code>delta</code> number of x to the right and
     * for negative numbers the same to the left.
     */
    fun withRelativeX(delta: Int) = if (delta == 0) this else withX(x + delta)

    /**
     * Creates a new [Position] object representing a position on the same x, but with a y offset by a
     * supplied value. Calling this method with delta 0 will return this, calling it with a positive delta
     * will return a grid position <code>delta</code> number of y to the down and for negative
     * numbers the same up.
     */
    fun withRelativeY(delta: Int) = if (delta == 0) this else withY(y + delta)

    /**
     * Creates a new [Position] object that is translated by an amount of x and y specified by another
     * [Position]. Same as calling `withRelativeY(translate.getYLength()).withRelativeX(translate.getXLength())`.
     */
    fun withRelative(translate: Position) = withRelativeY(translate.y)
            .withRelativeX(translate.x)

    /**
     * Transforms this [Position] to a [Size] so if
     * this position is Position(x=2, y=3) it will become
     * Size(x=2, y=3).
     */
    fun toSize() = Size.create(x, y)

    /**
     * Creates a [Position] which is relative to the top of the given [Component].
     * The x coordinate is used to shift right
     * The y coordinate is used to shift up
     */
    fun relativeToTopOf(component: Component) = component.position().let { (compX, compY) ->
        create(compX + x, maxOf(compY - y, 0))
    }

    /**
     * Creates a [Position] which is relative to the right of the given [Component].
     * The x coordinate is used to shift right
     * The y coordinate is used to shift down
     */
    fun relativeToRightOf(component: Component) = component.position().let { (compX, compY) ->
        create(
                x = compX + component.size().xLength + x,
                y = compY + y)
    }

    /**
     * Creates a [Position] which is relative to the bottom of the given [Component].
     * The x coordinate is used to shift right
     * The y coordinate is used to shift down
     */
    fun relativeToBottomOf(component: Component) = component.position().let { (compX, compY) ->
        create(
                x = compX + x,
                y = compY + component.size().yLength + y)
    }

    /**
     * Creates a [Position] which is relative to the left of the given [Component].
     * The x coordinate is used to shift left
     * The y coordinate is used to shift down
     */
    fun relativeToLeftOf(component: Component) = component.position().let { (compX, compY) ->
        create(maxOf(compX - x, 0), compY + y)
    }

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
         * This position can be considered as the default
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
            require(x >= 0) {
                "x must be greater than or equal to 0"
            }
            require(y >= 0) {
                "y must be greater than or equal to 0"
            }
            return GridPosition(x, y)
        }

        private fun create(x: Int, y: Int, pos: Position): Position {
            require(x >= 0) {
                "x must be greater than or equal to 0"
            }
            require(y >= 0) {
                "y must be greater than or equal to 0"
            }
            return when (pos) {
                is GridPosition -> GridPosition(x, y)
                is AbsolutePosition -> AbsolutePosition(x, y)
                else -> throw UnsupportedOperationException("Unsupported Position type: ${pos::class.simpleName}")
            }
        }

        private fun checkType(pos0: Position, pos1: Position) {
            require(pos0::class == pos1::class)
        }

        private val TOP_LEFT_CORNER = create(0, 0)
        private val OFFSET_1X1 = create(1, 1)
        private val DEFAULT_POSITION = TOP_LEFT_CORNER
        private val UNKNOWN = create(Int.MAX_VALUE, Int.MAX_VALUE)
    }
}
