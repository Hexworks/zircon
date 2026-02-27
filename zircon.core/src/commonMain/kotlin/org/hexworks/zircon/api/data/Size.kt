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