package org.hexworks.zircon.api.data.extensions

import org.hexworks.zircon.api.behavior.extensions.width
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.data.GridPosition
import org.hexworks.zircon.internal.data.PixelPosition

/**
 * Tells whether this [Position] is `UNKNOWN`.
 */
val Position.isPositionUnknown: Boolean
    get() = this === Position.UNKNOWN

/**
 * Tells whether this [Position] is not `UNKNOWN`.
 */
val Position.isPositionNotUnknown: Boolean
    get() = this !== Position.UNKNOWN

/**
 * Tells whether this [Position] has a negative component (x or y) or not.
 */
val Position.hasNegativeComponent: Boolean
    get() = x < 0 || y < 0


/**
 * Creates a new [Position] object representing a position with the same y index as this but with a
 * supplied x index.
 */
fun Position.withX(x: Int): Position =
    if (this.x == x) {
        this
    } else if (x == 0 && y == 0) {
        Position.ZERO
    } else {
        create(x, y, this)
    }

/**
 * Creates a new [Position] object representing a position with the same y index as this but with a
 * supplied y index.
 */
fun Position.withY(y: Int): Position =
    if (this.y == y) {
        this
    } else if (x == 0 && y == 0) {
        Position.ZERO
    } else {
        create(x, y, this)
    }

/**
 * Creates a new [Position] object representing a position on the same y, but with a x offset by a
 * supplied value. Calling this method with delta 0 will returnThis this, calling it with a positive
 * delta will returnThis a grid position <code>delta</code> number of x to the right and
 * for negative numbers the same to the left.
 */
fun Position.withRelativeX(delta: Int): Position = if (delta == 0) this else withX(x + delta)

/**
 * Creates a new [Position] object representing a position on the same x, but with a y offset by a
 * supplied value. Calling this method with delta 0 will returnThis this, calling it with a positive delta
 * will returnThis a grid position <code>delta</code> number of y to the down and for negative
 * numbers the same up.
 */
fun Position.withRelativeY(delta: Int): Position = if (delta == 0) this else withY(y + delta)

/**
 * Creates a new [Position] object that is translated by an amount of x and y specified by another
 * [Position]. Same as calling `withRelativeY(translate.getYLength()).withRelativeX(translate.getXLength())`.
 */
fun Position.withRelative(translate: Position): Position = withRelativeY(translate.y).withRelativeX(translate.x)


/**
 * Creates a [Position] which is relative to the top of the given [Component].
 * The x coordinate is used to shift right
 * The y coordinate is used to shift up
 */
fun Position.relativeToTopOf(component: Component): Position = component.position.let { (compX, compY) ->
    Position.create(compX + x, maxOf(compY - y, 0))
}

/**
 * Creates a [Position] which is relative to the right of the given [Component].
 * The x coordinate is used to shift right
 * The y coordinate is used to shift down
 */
fun Position.relativeToRightOf(component: Component): Position = component.position.let { (compX, compY) ->
    Position.create(compX + component.width + x, compY + y)
}

/**
 * Creates a [Position] which is relative to the bottom of the given [Component].
 * The x coordinate is used to shift right
 * The y coordinate is used to shift down
 */
fun Position.relativeToBottomOf(component: Component) = component.position.let { (compX, compY) ->
    Position.create(compX + x, compY + component.size.height + y)
}

/**
 * Creates a [Position] which is relative to the top left of the given [Component].
 * The x coordinate is used to shift left
 * The y coordinate is used to shift down
 */
fun Position.relativeToLeftOf(component: Component) = component.position.let { (compX, compY) ->
    Position.create(maxOf(compX - x, 0), compY + y)
}

/**
 * Transforms this [Position] to a [Size] so if
 * this position is Position(x=2, y=3) it will become
 * Size(x=2, y=3).
 */
fun Position.toSize(): Size = Size.create(x, y)

/**
 * Creates a new [Position3D] from the [x] and [y] components of this [Position]
 * and the given [z] value.
 */

fun Position.toPosition3D(z: Int): Position3D = Position3D.from2DPosition(this, z)

/**
 * Turns this [Position] to a [PixelPosition]. Has no effect if
 * `this` object is a [PixelPosition].
 */
fun Position.toPixelPosition(tileset: TilesetResource): PixelPosition =
    this as? PixelPosition
        ?: PixelPosition(x * tileset.width, y * tileset.height)

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

