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
data class Position3D(
    val x: Int,
    val y: Int,
    val z: Int
) : Comparable<Position3D> {

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

    companion object {

        /**
         * Factory method for [Position3D].
         */
        fun create(x: Int, y: Int, z: Int) = Position3D(x = x, y = y, z = z)

        /**
         * Creates a new [Position3D] from a [Position].
         * If `y` is not supplied it defaults to `0` (ground level).
         */
        fun from2DPosition(position: Position, z: Int = 0) = Position3D(
            x = position.x,
            y = position.y,
            z = z
        )

        val DEFAULT_POSITION = create(0, 0, 0)
        val UNKNOWN = create(Int.MAX_VALUE, Int.MAX_VALUE, Int.MAX_VALUE)
    }
}
