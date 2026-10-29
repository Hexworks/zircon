package org.hexworks.zircon.api.data.base

import org.hexworks.zircon.api.data.Position

/**
 * Base class for [Position] implementations. Subclasses only need to supply
 * [create], which builds a new position of the same kind; arithmetic and
 * ordering come from here.
 */
public abstract class BasePosition : Position {

    /**
     * Creates a new [Position] of the same kind as this one with the given coordinates.
     */
    protected abstract fun create(x: Int, y: Int): Position

    /**
     * Returns a new [Position] which is the sum of `x` and `y` in both [Position]s.
     * so `Position(x = 1, y = 1).plus(Position(x = 2, y = 2))` will be
     * `Position(x = 3, y = 3)`.
     */
    override operator fun plus(other: Position): Position {
        checkType(this, other)
        if (other == Position.ZERO) {
            return this
        }
        return create(x + other.x, y + other.y)
    }

    /**
     * Returns a new [Position] which is the difference of `x` and `y`  both [Position]s.
     * so `Position(x = 3, y = 3).minus(Position(x = 2, y = 2))` will be
     * `Position(x = 1, y = 1)`.
     */
    override operator fun minus(other: Position): Position {
        checkType(this, other)
        if (other == Position.ZERO) {
            return this
        }
        return create(x - other.x, y - other.y)
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
