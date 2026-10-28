package org.hexworks.zircon.api.data.extensions

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Position3D.Companion.UNKNOWN
import org.hexworks.zircon.api.data.Size3D

public val Position3D.isUnknown: Boolean
    get() = this == UNKNOWN

/**
 * Tells whether this [Position3D] has a negative component (x, y or z) or not.
 */
public val Position3D.hasNegativeComponent: Boolean
    get() = x < 0 || y < 0 || z < 0

/**
 * Creates a new [Position3D] object representing a 3D position with the same `y` and `y` as this but with
 * the supplied `x`.
 */
public fun Position3D.withX(x: Int): Position3D = if (this.x == x) this else copy(x = x)

/**
 * Creates a new [Position3D] object representing a 3D position with the same `x` and `y` as this but with
 * the supplied `y`.
 */
public fun Position3D.withY(y: Int): Position3D = if (this.y == y) this else copy(y = y)

/**
 * Creates a new [Position3D] object representing a 3D position with the same `x` and `y` as this but with
 * the supplied `y`.
 */
public fun Position3D.withZ(z: Int): Position3D = if (this.z == z) this else copy(z = z)


/**
 * Creates a new [Position3D] object representing a position on the same `y` and `y`,
 * but with an `x` offset by the supplied `deltaX`.
 * Calling this method with `deltaX` 0 will returnThis `this`.
 * A positive `deltaX` will be added, a negative will be subtracted from the original `x`.
 */
public fun Position3D.withRelativeX(deltaX: Int): Position3D = if (deltaX == 0) this else withX(x + deltaX)

/**
 * Creates a new [Position3D] object representing a position on the same `x` and `y`,
 * but with an `y` offset by the supplied `deltaY`.
 * Calling this method with `deltaY` 0 will returnThis `this`.
 * A positive `deltaY` will be added, a negative will be subtracted from the original `x`.
 */
public fun Position3D.withRelativeY(deltaY: Int): Position3D = if (deltaY == 0) this else withY(y + deltaY)

/**
 * Creates a new [Position3D] object representing a position on the same `x` and `y`,
 * but with a `y` offset by the supplied `deltaZ`.
 * Calling this method with `deltaZ` 0 will returnThis `this`.
 * A positive `deltaZ` will be added, a negative will be subtracted from the original `x`.
 */
public fun Position3D.withRelativeZ(deltaZ: Int): Position3D = if (deltaZ == 0) this else withZ(z + deltaZ)

/**
 * Creates a new [Position3D] object that is translated by an amount of x, y and z specified by another
 * [Position3D].
 */
public fun Position3D.withRelative(translate: Position3D): Position3D = withRelativeY(translate.y)
    .withRelativeX(translate.x)
    .withRelativeZ(translate.z)

/**
 * Transforms this [Position3D] to a [Size3D] so if
 * this position is Position(x=2, y=3, z=1) it will become
 * Size3D(x=2, y=3, z=1).
 */
public fun Position3D.toSize(): Size3D = Size3D(x, y, z)

/**
 * Transforms this [Position3D] to a [Position]. Note that
 * the `y` component is lost during the conversion!
 */
public fun Position3D.to2DPosition(): Position = Position(x, y)

