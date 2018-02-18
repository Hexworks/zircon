package org.codetome.zircon.api

import org.codetome.zircon.api.shape.RectangleFactory

/**
 * Dimensions in 2D space.
 * This class is immutable and cannot change its internal state after creation.
 */
@Suppress("DataClassPrivateConstructor")
data class Size private constructor(val xLength: Int,
                                    val yLength: Int) : Comparable<Size> {

    init {
        require(xLength >= 0) {
            "Size.xLength cannot be less than 0!"
        }
        require(yLength >= 0) {
            "Size.yLength cannot be less than 0!"
        }
    }

    operator fun plus(other: Size) = Size.of(xLength + other.xLength, yLength + other.yLength)

    operator fun minus(other: Size) = Size.of(xLength - other.xLength, yLength - other.yLength)

    override fun compareTo(other: Size) = (this.xLength * this.yLength).compareTo(other.xLength * other.yLength)

    /**
     * Creates a list of [Position]s in the order in which they should
     * be iterated when drawing (from left to right, then top to bottom).
     */
    fun fetchPositions(): List<Position> = (0 until yLength).flatMap { y ->
        (0 until xLength).map { x ->
            Position.of(x, y)
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
                .buildRectangle(Position.DEFAULT_POSITION, this)
                .getPositions()
    }

    fun fetchTopLeftPosition() = Position.TOP_LEFT_CORNER

    fun fetchTopRightPosition() = Position.of(xLength - 1, 0)

    fun fetchBottomLeftPosition() = Position.of(0, yLength - 1)

    fun fetchBottomRightPosition() = Position.of(xLength - 1, yLength - 1)

    /**
     * Creates a new size based on this size, but with a different xLength.
     */
    fun withXLength(xLength: Int): Size {
        if (this.xLength == xLength) {
            return this
        }
        return returnZeroIfZero(Size(xLength, this.yLength))
    }

    /**
     * Creates a new size based on this size, but with a different yLength.
     */
    fun withYLength(yLength: Int): Size {
        if (this.yLength == yLength) {
            return this
        }
        return returnZeroIfZero(Size(this.xLength, yLength))
    }

    /**
     * Creates a new [Size] object representing a size with the same number of yLength, but with
     * a xLength size offset by a supplied value. Calling this method with delta 0 will return this,
     * calling it with a positive delta will return
     * a terminal size <code>delta</code> number of xLength wider and for negative numbers shorter.
     */
    fun withRelativeXLength(delta: Int): Size {
        if (delta == 0) {
            return this
        }
        return withXLength(xLength + delta)
    }

    /**
     * Creates a new [Size] object representing a size with the same number of xLength, but with a yLength
     * size offset by a supplied value. Calling this method with delta 0 will return this, calling
     * it with a positive delta will return
     * a terminal size <code>delta</code> number of yLength longer and for negative numbers shorter.
     */
    fun withRelativeYLength(delta: Int): Size {
        if (delta == 0) {
            return this
        }
        return withYLength(yLength + delta)
    }

    /**
     * Creates a new [Size] object representing a size based on this object's size but with a delta applied.
     * This is the same as calling `withRelativeXLength(delta.getXLength()).withRelativeYLength(delta.getYLength())`
     */
    fun withRelative(delta: Size): Size {
        return withRelativeYLength(delta.yLength).withRelativeXLength(delta.xLength)
    }

    /**
     * Takes a different [Size] and returns a new [Size] that has the largest dimensions of the two,
     * measured separately. So calling 3x5 on a 5x3 will return 5x5.
     */
    fun max(other: Size): Size {
        return withXLength(Math.max(xLength, other.xLength))
                .withYLength(Math.max(yLength, other.yLength))
    }

    /**
     * Takes a different [Size] and returns a new [Size] that has the smallest dimensions of the two,
     * measured separately. So calling 3x5 on a 5x3 will return 3x3.
     */
    fun min(other: Size): Size {
        return withXLength(Math.min(xLength, other.xLength))
                .withYLength(Math.min(yLength, other.yLength))
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

    /**
     * TODO: refactor DefaultBoundable to call to this. Implement other DefaultBoundable methods.
     */
    fun containsPosition(position: Position) = xLength > position.x && yLength > position.y

    /////////////////////////////
    /// DEPRECATED ZONE
    ///
    /// HERE BE DRAGONS
    /////////////////////////////

    @Deprecated(message = "This is obsolete, use the value `xLength` instead",
            replaceWith = ReplaceWith(
                    expression = "xLength",
                    imports = ["org.codetome.zircon.api"]))
    fun getColumns() = xLength

    @Deprecated(message = "This is obsolete, use the value `yLength` instead",
            replaceWith = ReplaceWith(
                    expression = "yLength",
                    imports = ["org.codetome.zircon.api"]))
    fun getRows() = yLength

    @Deprecated(message = "This is obsolete, use `withY` instead",
            replaceWith = ReplaceWith(
                    expression = ".withY",
                    imports = ["org.codetome.zircon.api"]))
    fun withRows(row: Int) = withYLength(row)

    @Deprecated(message = "This is obsolete, use `withX` instead",
            replaceWith = ReplaceWith(
                    expression = ".withX",
                    imports = ["org.codetome.zircon.api"]))
    fun withColumns(column: Int) = withXLength(column)

    @Deprecated(message = "This is obsolete, use `withRelativeX` instead",
            replaceWith = ReplaceWith(
                    expression = ".withRelativeX",
                    imports = ["org.codetome.zircon.api"]))
    fun withRelativeColumns(delta: Int) = withRelativeXLength(delta)

    @Deprecated(message = "This is obsolete, use `withRelativeY` instead",
            replaceWith = ReplaceWith(
                    expression = ".withRelativeY",
                    imports = ["org.codetome.zircon.api"]))
    fun withRelativeRows(delta: Int) = withRelativeYLength(delta)

    private fun returnZeroIfZero(size: Size): Size {
        return if (size.xLength == 0 || size.yLength == 0) {
            ZERO
        } else {
            size
        }
    }

    companion object {

        @JvmField
        val UNKNOWN = Size(Int.MAX_VALUE, Int.MAX_VALUE)

        @JvmField
        val DEFAULT_TERMINAL_SIZE = Size(80, 24)

        @JvmField
        val ZERO = Size(0, 0)

        @JvmField
        val ONE = Size(1, 1)

        /**
         * Factory method for [Size].
         */
        @JvmStatic
        fun of(xLength: Int, yLength: Int) = Size(xLength, yLength)
    }
}
