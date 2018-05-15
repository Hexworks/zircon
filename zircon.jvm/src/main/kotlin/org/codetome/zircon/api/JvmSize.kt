package org.codetome.zircon.api

import org.codetome.zircon.api.shape.RectangleFactory

/**
 * Dimensions in 2D space.
 * This class is immutable and cannot change its internal state after creation.
 */
data class JvmSize(override val xLength: Int,
                   override val yLength: Int) : Size {

    init {
        require(xLength >= 0) {
            "Size.xLength cannot be less than 0!"
        }
        require(yLength >= 0) {
            "Size.yLength cannot be less than 0!"
        }
    }

    /**
     * Creates a list of [Position]s which represent the
     * bounding box of this size. So for example a size of (3x3)
     * will have a bounding box of
     * `[(0, 0), (1, 0), (2, 0), (0, 1), (2, 1), (0, 2), (1, 2), (2, 2)]`
     */
    fun fetchBoundingBoxPositions(): Set<Position> {
        return RectangleFactory
                .buildRectangle(Position.defaultPosition(), this)
                .getPositions()
    }

    /**
     * Takes a different [Size] and returns a new [Size] that has the largest dimensions of the two,
     * measured separately. So calling 3x5 on a 5x3 will return 5x5.
     */
    override fun max(other: Size): Size {
        return withXLength(Math.max(xLength, other.xLength))
                .withYLength(Math.max(yLength, other.yLength))
    }

    /**
     * Takes a different [Size] and returns a new [Size] that has the smallest dimensions of the two,
     * measured separately. So calling 3x5 on a 5x3 will return 3x3.
     */
    override fun min(other: Size): Size {
        return withXLength(Math.min(xLength, other.xLength))
                .withYLength(Math.min(yLength, other.yLength))
    }
}
