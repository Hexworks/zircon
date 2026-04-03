package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.behavior.Additive
import org.hexworks.zircon.api.behavior.extensions.height
import org.hexworks.zircon.api.behavior.extensions.width
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.data.extensions.toPosition
import org.hexworks.zircon.api.data.extensions.withRelative
import org.hexworks.zircon.api.data.extensions.withRelativeX
import org.hexworks.zircon.api.data.extensions.withRelativeY
import org.hexworks.zircon.internal.data.GridPosition

/**
 * Represents a coordinate on a 2D plane. [Position]
 * destructures into [x] and [y].
 */
interface Position : Comparable<Position>, Additive<Position> {

    val x: Int
    val y: Int

    operator fun component1() = x

    operator fun component2() = y

    /**
     * Returns a new [Position] which is the sum of `x` and `y` in both [Position]s.
     * so `Position(x = 1, y = 1).plus(Position(x = 2, y = 2))` will be
     * `Position(x = 3, y = 3)`.
     */
    override operator fun plus(other: Position): Position

    /**
     * Returns a new [Position] which is the difference of `x` and `y`  both [Position]s.
     * so `Position(x = 3, y = 3).minus(Position(x = 2, y = 2))` will be
     * `Position(x = 1, y = 1)`.
     */
    override operator fun minus(other: Position): Position


    companion object {

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

        val ZERO = create(0, 0)
        val OFFSET_1X1 = create(1, 1)
        val UNKNOWN = create(Int.MAX_VALUE, Int.MAX_VALUE)
    }
}
