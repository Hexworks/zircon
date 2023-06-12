package org.hexworks.zircon.api.data

/**
 * Represents a coordinate in 3D space. Extends [Position] with
 * a `z` dimension. Use [Position3D.from2DPosition] and [Position3D.to2DPosition]
 * to convert between the two. The `z` dimension represents the up and down axis.
 *
 * Explanation:
 * <pre>
 *         ^ (z axis, positive direction)
 *         \
 *         \
 *         \
 *         \
 *         O---------> (x axis, positive direction)
 *        /
 *      /
 *    /
 *  L  (y axis, positive direction)
 *
 *</pre>
 */
@Suppress("DataClassPrivateConstructor")
data class Position3D private constructor(
    val x: Int,
    val y: Int,
    val z: Int
) : Comparable<Position3D> {

    val isUnknown: Boolean
        get() = this == UNKNOWN

    /**
     * Tells whether this [Position3D] has a negative component (x, y or z) or not.
     */
    val hasNegativeComponent: Boolean
        get() = x < 0 || y < 0 || z < 0

    override fun compareTo(other: Position3D): Int {
        return when {
            other.z > z -> -1
            other.z < z -> 1
            else -> {
                when {
                    other.y > y -> -1
                    other.y < y -> 1
                    else -> {
                        when {
                            other.x > x -> -1
                            other.x < x -> 1
                            else -> {
                                0
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Returns a new [Position3D] which is the sum of `x`, `y` and `z` in both [Position3D]s.
     * so `Position3D(x = 1, y = 1, z = 1).plus(Position3D(x = 2, y = 2, z = 2))` will be
     * `Position3D(x = 3, y = 3, z = 3)`.
     */
    operator fun plus(other: Position3D) = Position3D(
        x = this.x + other.x,
        y = this.y + other.y,
        z = this.z + other.z
    )

    /**
     * Returns a new [Position3D] which is the difference of `x`, `y` and `z` in both [Position3D]s.
     * so `Position3D(x = 3, y = 3, z = 3).minus(Position3D(x = 2, y = 2, z = 2))` will be
     * `Position3D(x = 1, y = 1, z = 1)`.
     */
    operator fun minus(other: Position3D) = Position3D(
        x = this.x - other.x,
        y = this.y - other.y,
        z = this.z - other.z
    )

    /**
     * Creates a new [Position3D] object representing a 3D position with the same `y` and `y` as this but with
     * the supplied `x`.
     */
    fun withX(x: Int) = if (this.x == x) this else copy(x = x)

    /**
     * Creates a new [Position3D] object representing a 3D position with the same `x` and `y` as this but with
     * the supplied `y`.
     */
    fun withY(y: Int) = if (this.y == y) this else copy(y = y)

    /**
     * Creates a new [Position3D] object representing a 3D position with the same `x` and `y` as this but with
     * the supplied `y`.
     */
    fun withZ(z: Int) = if (this.z == z) this else copy(z = z)


    /**
     * Creates a new [Position3D] object representing a position on the same `y` and `y`,
     * but with an `x` offset by the supplied `deltaX`.
     * Calling this method with `deltaX` 0 will returnThis `this`.
     * A positive `deltaX` will be added, a negative will be subtracted from the original `x`.
     */
    fun withRelativeX(deltaX: Int) = if (deltaX == 0) this else withX(x + deltaX)

    /**
     * Creates a new [Position3D] object representing a position on the same `x` and `y`,
     * but with an `y` offset by the supplied `deltaY`.
     * Calling this method with `deltaY` 0 will returnThis `this`.
     * A positive `deltaY` will be added, a negative will be subtracted from the original `x`.
     */
    fun withRelativeY(deltaY: Int) = if (deltaY == 0) this else withY(y + deltaY)

    /**
     * Creates a new [Position3D] object representing a position on the same `x` and `y`,
     * but with a `y` offset by the supplied `deltaZ`.
     * Calling this method with `deltaZ` 0 will returnThis `this`.
     * A positive `deltaZ` will be added, a negative will be subtracted from the original `x`.
     */
    fun withRelativeZ(deltaZ: Int) = if (deltaZ == 0) this else withZ(z + deltaZ)

    /**
     * Creates a new [Position3D] object that is translated by an amount of x, y and z specified by another
     * [Position3D].
     */
    fun withRelative(translate: Position3D) = withRelativeY(translate.y)
        .withRelativeX(translate.x)
        .withRelativeZ(translate.z)

    /**
     * Transforms this [Position3D] to a [Size3D] so if
     * this position is Position(x=2, y=3, z=1) it will become
     * Size3D(x=2, y=3, z=1).
     */
    fun toSize(): Size3D = Size3D.create(x, y, z)

    /**
     * Transforms this [Position3D] to a [Position]. Note that
     * the `y` component is lost during the conversion!
     */
    fun to2DPosition() = Position.create(x, y)

    companion object {

        /**
         * Factory method for [Position3D].
         */
        fun create(x: Int, y: Int, z: Int) = Position3D(x = x, y = y, z = z)

        /**
         * Position3d(0, 0, 0)
         */
        fun defaultPosition() = DEFAULT_POSITION

        fun unknown() = UNKNOWN

        /**
         * Creates a new [Position3D] from a [Position].
         * If `y` is not supplied it defaults to `0` (ground level).
         */
        fun from2DPosition(position: Position, z: Int = 0) = Position3D(
            x = position.x,
            y = position.y,
            z = z
        )

        private val DEFAULT_POSITION = create(0, 0, 0)
        private val UNKNOWN = create(Int.MAX_VALUE, Int.MAX_VALUE, Int.MAX_VALUE)
    }
}
