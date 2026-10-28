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
public interface Position : Comparable<Position>, Additive<Position> {

    public val x: Int
    public val y: Int

    public operator fun component1(): Int = x

    public operator fun component2(): Int = y

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


    public companion object {

        /**
         * Creates a new [Position] using the given `x` and `y` values.
         */
        public operator fun invoke(x: Int, y: Int): Position {
            return GridPosition(x, y)
        }

        /**
         * Returns the top left position of the given [Component].
         */
        public fun topLeftOf(component: Component): Position = component.position

        /**
         * Returns the top right position of the given [Component].
         */
        public fun topRightOf(component: Component): Position = component.position.withRelativeX(component.width)

        /**
         * Returns the bottom left position of the given [Component].
         */
        public fun bottomLeftOf(component: Component): Position = component.position.withRelativeY(component.height)

        /**
         * Returns the bottom right position of the given [Component].
         */
        public fun bottomRightOf(component: Component): Position = component.position.withRelative(component.size.toPosition())

        public val ZERO: Position = invoke(0, 0)
        public val OFFSET_1X1: Position = invoke(1, 1)
        public val UNKNOWN: Position = invoke(Int.MAX_VALUE, Int.MAX_VALUE)
    }
}
