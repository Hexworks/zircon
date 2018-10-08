package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.shape.RectangleFactory
import org.hexworks.zircon.api.util.Math
import org.hexworks.zircon.internal.data.DefaultSize

/**
 * Represents a rectangular area in a 2D space.
 * This class is immutable and cannot change its internal state after creation.
 */
interface Size : Comparable<Size> {

    val width: Int

    val height: Int

    operator fun plus(other: Size) = create(width + other.width, height + other.height)

    operator fun minus(other: Size) = create(width - other.width, height - other.height)

    operator fun component1() = width

    operator fun component2() = height

    override fun compareTo(other: Size) = (this.width * this.height).compareTo(other.width * other.height)

    fun isUnknown() = this === UNKNOWN

    /**
     * Creates a list of [Position]s in the order in which they should
     * be iterated when drawing (first rows, then columns in those rows).
     */
    fun fetchPositions(): Iterable<Position> = Iterable {
        var currY = 0
        var currX = 0
        val endX = width
        val endY = height

        object : Iterator<Position> {

            override fun hasNext() = currY < endY && currX < endX

            override fun next(): Position {
                return Position.create(currX, currY).also {
                    currX++
                    if (currX == endX) {
                        currY++
                        if (currY < endY) {
                            currX = 0
                        }
                    }
                }
            }
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
                .positions()
    }

    fun fetchTopLeftPosition() = Position.topLeftCorner()

    fun fetchTopRightPosition() = Position.create(width - 1, 0)

    fun fetchBottomLeftPosition() = Position.create(0, height - 1)

    fun fetchBottomRightPosition() = Position.create(width - 1, height - 1)

    fun withWidth(width: Int): Size = withXLength(width)

    fun withRelativeWidth(delta: Int): Size = withRelativeXLength(delta)

    fun withHeight(height: Int): Size = withYLength(height)

    fun withRelativeHeight(delta: Int): Size = withRelativeYLength(delta)

    /**
     * Creates a new size based on this size, but with a different xLength.
     */
    fun withXLength(xLength: Int): Size {
        if (this.width == xLength) {
            return this
        }
        return create(xLength, this.height)
    }

    /**
     * Creates a new size based on this size, but with a different yLength.
     */
    fun withYLength(yLength: Int): Size {
        if (this.height == yLength) {
            return this
        }
        return create(this.width, yLength)
    }

    /**
     * Creates a new [Size] object representing a size with the same number of yLength, but with
     * a xLength size offset by a supplied value. Calling this method with delta 0 will returnThis this,
     * calling it with a positive delta will returnThis
     * a grid size <code>delta</code> number of xLength wider and for negative numbers shorter.
     */
    fun withRelativeXLength(delta: Int): Size {
        if (delta == 0) {
            return this
        }
        return withXLength(width + delta)
    }

    /**
     * Creates a new [Size] object representing a size with the same number of xLength, but with a yLength
     * size offset by a supplied value. Calling this method with delta 0 will returnThis this, calling
     * it with a positive delta will returnThis
     * a grid size <code>delta</code> number of yLength longer and for negative numbers shorter.
     */
    fun withRelativeYLength(delta: Int): Size {
        if (delta == 0) {
            return this
        }
        return withYLength(height + delta)
    }

    /**
     * Creates a new [Size] object representing a size based on this object's size but with a delta applied.
     * This is the same as calling `withRelativeXLength(delta.getXLength()).withRelativeYLength(delta.getYLength())`
     */
    fun withRelative(delta: Size): Size {
        return withRelativeYLength(delta.height).withRelativeXLength(delta.width)
    }

    /**
     * Takes a different [Size] and returns a new [Size] that has the largest dimensions of the two,
     * measured separately. So calling 3x5 on a 5x3 will returnThis 5x5.
     */
    fun max(other: Size): Size {
        return withXLength(Math.max(width, other.width))
                .withYLength(Math.max(height, other.height))
    }

    /**
     * Takes a different [Size] and returns a new [Size] that has the smallest dimensions of the two,
     * measured separately. So calling 3x5 on a 5x3 will returnThis 3x3.
     */
    fun min(other: Size): Size {
        return withXLength(Math.min(width, other.width))
                .withYLength(Math.min(height, other.height))
    }

    /**
     * Returns itself if it is equal to the supplied size, otherwise the supplied size.
     * You can use this if you have a size field which is frequently recalculated but often resolves
     * to the same size; it will keep the same object
     * in memory instead of swapping it out every cycle.
     */
    fun with(size: Size): Size {
        if (equals(size)) {
            return this
        }
        return size
    }

    fun containsPosition(position: Position) = width > position.x && height > position.y

    fun toPosition() = Position.create(width, height)

    fun toBounds(): Rect = toBounds(Position.defaultPosition())

    fun toBounds(position: Position): Rect = Rect.create(position, this)

    /**
     * Tells whether this [Size] has a negative component (xLength or yLength) or not.
     */
    fun hasNegativeComponent(): Boolean = width < 0 || height < 0

    companion object {

        /**
         * Represents a [Size] which is an unknown (can be used instead of a `null` value).
         */
        fun unknown() = UNKNOWN

        /**
         * The default grid size is (80 * 24)
         */
        fun defaultTerminalSize() = DEFAULT_TERMINAL_SIZE

        /**
         * Size of (0 * 0).
         */
        fun zero() = ZERO

        /**
         * Size of (1 * 1).
         */
        fun one() = ONE

        /**
         * Creates a new [Size] using the given `xLength` (width) and `yLength` (height).
         */
        fun create(xLength: Int, yLength: Int): Size = DefaultSize(xLength, yLength)

        private val UNKNOWN = create(Int.MAX_VALUE, Int.MAX_VALUE)
        private val DEFAULT_TERMINAL_SIZE = create(60, 30)
        private val ZERO = create(0, 0)
        private val ONE = create(1, 1)
    }
}
