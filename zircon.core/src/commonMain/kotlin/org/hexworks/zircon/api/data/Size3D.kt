package org.hexworks.zircon.api.data

/**
 * Represents the size of a 3D space. Extends [org.hexworks.zircon.api.data.Size]
 * with a `zLength` (z axis) dimension.
 * Explanation:
 *         ^ (zLength, z axis, positive direction)
 *         \
 *         \
 *         \
 *         \
 *         O---------> (xLength, x axis, positive direction)
 *        /
 *      /
 *    /
 *  L
 * (yLength, y axis, positive direction)
 *
 */
data class Size3D(
    val xLength: Int,
    val yLength: Int,
    val zLength: Int
) : Comparable<Size3D> {


    override fun compareTo(other: Size3D): Int {
        return when {
            fetchPositionCount() > other.fetchPositionCount() -> 1
            fetchPositionCount() < other.fetchPositionCount() -> -1
            else -> 0
        }
    }

    private fun fetchPositionCount(): Long = xLength.toLong() * yLength.toLong() * zLength.toLong()

    companion object {

        fun one() = create(1, 1, 1)

        /**
         * Factory method for [Size3D].
         */
        fun create(xLength: Int, yLength: Int, zLength: Int): Size3D {
            require(listOf(xLength, yLength, zLength).all { it >= 0 }) {
                "Can't create a Size3D with a negative length."
            }
            return Size3D(
                xLength = xLength,
                yLength = yLength,
                zLength = zLength
            )
        }

        /**
         * Creates a new [Size3D] from a [Size].
         * If `zLength` is not supplied, it defaults to `0`.
         */
        fun from2DSize(size: Size, zLength: Int = 0) = Size3D(
            xLength = size.width,
            yLength = size.height,
            zLength = zLength
        )
    }
}
