package org.hexworks.zircon.api.data.base

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.data.GridPosition
import org.hexworks.zircon.internal.data.PixelPosition

/**
 * Base class for [Position] implementations.
 */
abstract class BasePosition : Position {

    /**
     * Tells whether this [Position] is `UNKNOWN`.
     */
    override val isUnknown: Boolean
        get() = this === Position.unknown()

    /**
     * Tells whether this [Position] is not `UNKNOWN`.
     */
    override val isNotUnknown: Boolean
        get() = this !== Position.unknown()

    /**
     * Tells whether this [Position] has a negative component (x or y) or not.
     */
    override val hasNegativeComponent: Boolean
        get() = x < 0 || y < 0

    /**
     * Turns this [Position] to a [PixelPosition]. Has no effect if
     * `this` object is a [PixelPosition].
     */
    override fun toPixelPosition(tileset: TilesetResource): PixelPosition =
        this as? PixelPosition
            ?: PixelPosition(x * tileset.width, y * tileset.height)

    /**
     * Returns a new [Position] which is the sum of `x` and `y` in both [Position]s.
     * so `Position(x = 1, y = 1).plus(Position(x = 2, y = 2))` will be
     * `Position(x = 3, y = 3)`.
     */
    override operator fun plus(other: Position): Position {
        checkType(this, other)
        if (other == Position.zero()) {
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
        if (other == Position.zero()) {
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

    /**
     * Creates a new [Position] object representing a position with the same y index as this but with a
     * supplied x index.
     */
    override fun withX(x: Int): Position =
        if (this.x == x) {
            this
        } else if (x == 0 && y == 0) {
            Position.zero()
        } else {
            create(x, y, this)
        }

    /**
     * Creates a new [Position] object representing a position with the same y index as this but with a
     * supplied y index.
     */
    override fun withY(y: Int): Position =
        if (this.y == y) {
            this
        } else if (x == 0 && y == 0) {
            Position.zero()
        } else {
            create(x, y, this)
        }

    /**
     * Creates a new [Position] object representing a position on the same y, but with a x offset by a
     * supplied value. Calling this method with delta 0 will returnThis this, calling it with a positive
     * delta will returnThis a grid position <code>delta</code> number of x to the right and
     * for negative numbers the same to the left.
     */
    override fun withRelativeX(delta: Int): Position = if (delta == 0) this else withX(x + delta)

    /**
     * Creates a new [Position] object representing a position on the same x, but with a y offset by a
     * supplied value. Calling this method with delta 0 will returnThis this, calling it with a positive delta
     * will returnThis a grid position <code>delta</code> number of y to the down and for negative
     * numbers the same up.
     */
    override fun withRelativeY(delta: Int): Position = if (delta == 0) this else withY(y + delta)

    /**
     * Creates a new [Position] object that is translated by an amount of x and y specified by another
     * [Position]. Same as calling `withRelativeY(translate.getYLength()).withRelativeX(translate.getXLength())`.
     */
    override fun withRelative(translate: Position): Position = withRelativeY(translate.y).withRelativeX(translate.x)

    /**
     * Transforms this [Position] to a [Size] so if
     * this position is Position(x=2, y=3) it will become
     * Size(x=2, y=3).
     */
    override fun toSize(): Size = Size.create(x, y)

    override fun toPosition3D(z: Int): Position3D = Position3D.from2DPosition(this, z)

    /**
     * Creates a [Position] which is relative to the top of the given [Component].
     * The x coordinate is used to shift right
     * The y coordinate is used to shift up
     */
    override fun relativeToTopOf(component: Component): Position = component.position.let { (compX, compY) ->
        Position.create(compX + x, maxOf(compY - y, 0))
    }

    /**
     * Creates a [Position] which is relative to the right of the given [Component].
     * The x coordinate is used to shift right
     * The y coordinate is used to shift down
     */
    override fun relativeToRightOf(component: Component): Position = component.position.let { (compX, compY) ->
        Position.create(compX + component.width + x, compY + y)
    }

    /**
     * Creates a [Position] which is relative to the bottom of the given [Component].
     * The x coordinate is used to shift right
     * The y coordinate is used to shift down
     */
    override fun relativeToBottomOf(component: Component) = component.position.let { (compX, compY) ->
        Position.create(compX + x, compY + component.size.height + y)
    }

    /**
     * Creates a [Position] which is relative to the top left of the given [Component].
     * The x coordinate is used to shift left
     * The y coordinate is used to shift down
     */
    override fun relativeToLeftOf(component: Component) = component.position.let { (compX, compY) ->
        Position.create(maxOf(compX - x, 0), compY + y)
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

    /**
     * Creates a [Position] of the same type with the given `x` and `y` as the given `pos`.
     */
    private fun create(x: Int, y: Int, pos: Position): Position {
        return when (pos) {
            is GridPosition -> GridPosition(x, y)
            is PixelPosition -> PixelPosition(x, y)
            else -> throw UnsupportedOperationException("Unsupported Position type: ${pos::class.simpleName}")
        }
    }

    private fun checkType(pos0: Position, pos1: Position) {
        require(pos0::class == pos1::class)
    }
}
