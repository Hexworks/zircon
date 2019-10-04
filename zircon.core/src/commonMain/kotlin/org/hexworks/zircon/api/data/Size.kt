package org.hexworks.zircon.api.data

import org.hexworks.zircon.internal.data.DefaultSize

/**
 * Represents a rectangular area in a 2D space.
 * This class is immutable and cannot change its internal state after creation.
 * [Size] supports destructuring to [width] and [height].
 */
interface Size : Comparable<Size> {

    val width: Int
    val height: Int

    operator fun plus(other: Size): Size

    operator fun minus(other: Size): Size

    operator fun component1() = width

    operator fun component2() = height

    /**
     * Tells whether this [Size] **is** the same as [Size.unknown].
     */
    val isUnknown: Boolean

    /**
     * Tells whether this [Size] **is not** the same as [Size.unknown].
     */
    val isNotUnknown: Boolean

    /**
     * Creates a list of [Position]s in the order in which they should
     * be iterated when drawing (first rows, then columns in those rows).
     */
    fun fetchPositions(): Iterable<Position>

    /**
     * Creates a list of [Position]s which represent the
     * bounding box of this size. So for example a size of (3x3)
     * will have a bounding box of
     * `[(0, 0), (1, 0), (2, 0), (0, 1), (2, 1), (0, 2), (1, 2), (2, 2)]`
     */
    fun fetchBoundingBoxPositions(): Set<Position>

    fun fetchTopLeftPosition(): Position

    fun fetchTopRightPosition(): Position

    fun fetchBottomLeftPosition(): Position

    fun fetchBottomRightPosition(): Position

    /**
     * Creates a new size based on this size, but with a different width.
     */
    fun withWidth(width: Int): Size

    /**
     * Creates a new size based on this size, but with a different height.
     */
    fun withHeight(height: Int): Size

    /**
     * Creates a new [Size] object representing a size with the same number of height, but with
     * a width size offset by a supplied value. Calling this method with delta 0 will returnThis this,
     * calling it with a positive delta will returnThis
     * a grid size <code>delta</code> number of width wider and for negative numbers shorter.
     */
    fun withRelativeWidth(delta: Int): Size

    /**
     * Creates a new [Size] object representing a size with the same number of width, but with a height
     * size offset by a supplied value. Calling this method with delta 0 will returnThis this, calling
     * it with a positive delta will returnThis
     * a grid size <code>delta</code> number of height longer and for negative numbers shorter.
     */
    fun withRelativeHeight(delta: Int): Size

    /**
     * Creates a new [Size] object representing a size based on this object's size but with a delta applied.
     * This is the same as calling `withRelativeXLength(delta.getXLength()).withRelativeYLength(delta.getYLength())`
     */
    fun withRelative(delta: Size): Size

    /**
     * Takes a different [Size] and returns a new [Size] that has the largest dimensions of the two,
     * measured separately. So calling 3x5 on a 5x3 will returnThis 5x5.
     */
    fun max(other: Size): Size

    /**
     * Takes a different [Size] and returns a new [Size] that has the smallest dimensions of the two,
     * measured separately. So calling 3x5 on a 5x3 will returnThis 3x3.
     */
    fun min(other: Size): Size

    /**
     * Returns itself if it is equal to the supplied size, otherwise the supplied size.
     * You can use this if you have a size field which is frequently recalculated but often resolves
     * to the same size; it will keep the same object
     * in memory instead of swapping it out every cycle.
     */
    fun with(size: Size): Size

    /**
     * Tells whether this [Size] contains the given [Position].
     * Works in the same way as [Rect.containsPosition].
     */
    fun containsPosition(position: Position): Boolean

    /**
     * Converts this [Size] to a [Position]:
     * [Size.width] to [Position.x] and [Size.height] to [Position.y]
     */
    fun toPosition(): Position

    /**
     * Converts this [Size] to a [Rect] using [Position.zero].
     */
    fun toRect(): Rect

    /**
     * Converts this [Size] to a [Rect] with the given [Position].
     */
    fun toRect(position: Position): Rect

    companion object {

        /**
         * Represents a [Size] which is an unknown (can be used instead of a `null` value).
         */
        fun unknown() = UNKNOWN

        /**
         * The default grid size is (80 * 24)
         */
        fun defaultGridSize() = DEFAULT_GRID_SIZE

        /**
         * Size of (0 * 0).
         */
        fun zero() = ZERO

        /**
         * Size of (1 * 1).
         */
        fun one() = ONE

        /**
         * Creates a new [Size] using the given `width` (width) and `height` (height).
         */
        fun create(width: Int, height: Int): Size = DefaultSize(width, height)

        private val UNKNOWN = create(Int.MAX_VALUE, Int.MAX_VALUE)
        private val DEFAULT_GRID_SIZE = create(60, 30)
        private val ZERO = create(0, 0)
        private val ONE = create(1, 1)
    }
}
