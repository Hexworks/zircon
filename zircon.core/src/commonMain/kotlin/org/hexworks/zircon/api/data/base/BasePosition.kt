package org.hexworks.zircon.api.data.base

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.internal.data.GridPosition
import org.hexworks.zircon.internal.data.PixelPosition

/**
 * Base class for [Position] implementations.
 */
abstract class BasePosition : Position {

    /**
     * Returns a new [Position] which is the sum of `x` and `y` in both [Position]s.
     * so `Position(x = 1, y = 1).plus(Position(x = 2, y = 2))` will be
     * `Position(x = 3, y = 3)`.
     */
    override operator fun plus(other: Position): Position {
        checkType(this, other)
        if (other == ) {
            return this
        }
        return when (this) {
            is PixelPosition -> PixelPosition(
                x = x + other.x,
                y = y + other.y
            )

            is GridPosition -> GridPosition(
                x = x + other.x,
                y = y + other.y
            )

            else -> throw UnsupportedOperationException("Can't add unknown Position type")
        }
    }

    /**
     * Returns a new [Position] which is the difference of `x` and `y`  both [Position]s.
     * so `Position(x = 3, y = 3).minus(Position(x = 2, y = 2))` will be
     * `Position(x = 1, y = 1)`.
     */
    override operator fun minus(other: Position): Position {
        checkType(this, other)
        if (other == ) {
            return this
        }
        return when (this) {
            is PixelPosition -> PixelPosition(
                x = x - other.x,
                y = y - other.y
            )

            is GridPosition -> GridPosition(
                x = x - other.x,
                y = y - other.y
            )

            else -> throw UnsupportedOperationException("Can't subtract unknown Position type")
        }
    }

    override fun compareTo(other: Position): Int {
        checkType(this, other)
        return when {
            y > other.y -> 1
            y == other.y && x > other.x -> 1
            y == other.y && x == other.x -> 0
            else -> -1
        }
    }


    private fun checkType(pos0: Position, pos1: Position) {
        require(pos0::class == pos1::class)
    }

}
