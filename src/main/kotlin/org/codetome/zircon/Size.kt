package org.codetome.zircon

/**
 * Terminal dimensions in 2D space, measured in number of rows and columns.
 * This class is immutable and cannot change its internal state after creation.
 */
data class Size(val columns: Int,
                val rows: Int) {

    init {
        require(columns >= 0) {
            "Size.columns cannot be less than 0!"
        }
        require(rows >= 0) {
            "Size.rows cannot be less than 0!"
        }
    }

    /**
     * Creates a list of [Position]s in the order in which they should
     * be iterated when drawing (from left to right, then top to bottom).
     */
    fun fetchPositions(): List<Position> = (0 until rows).flatMap { row ->
        (0 until columns).map { column ->
            Position.of(column, row)
        }
    }

    /**
     * Creates a new size based on this size, but with a different width.
     */
    fun withColumns(columns: Int): Size {
        if (this.columns == columns) {
            return this
        }
        return returnZeroIfZero(Size(columns, this.rows))
    }

    /**
     * Creates a new size based on this size, but with a different height.
     */
    fun withRows(rows: Int): Size {
        if (this.rows == rows) {
            return this
        }
        return returnZeroIfZero(Size(this.columns, rows))
    }

    /**
     * Creates a new [Size] object representing a size with the same number of rows, but with a column size offset by a
     * supplied value. Calling this method with delta 0 will return this, calling it with a positive delta will return
     * a terminal size <code>delta</code> number of columns wider and for negative numbers shorter.
     */
    fun withRelativeColumns(delta: Int): Size {
        if (delta == 0) {
            return this
        }
        return withColumns(columns + delta)
    }

    /**
     * Creates a new [Size] object representing a size with the same number of columns, but with a row size offset by a
     * supplied value. Calling this method with delta 0 will return this, calling it with a positive delta will return
     * a terminal size <code>delta</code> number of rows longer and for negative numbers shorter.
     */
    fun withRelativeRows(delta: Int): Size {
        if (delta == 0) {
            return this
        }
        return withRows(rows + delta)
    }

    /**
     * Creates a new [Size] object representing a size based on this object's size but with a delta applied.
     * This is the same as calling `withRelativeColumns(delta.getColumns()).withRelativeRows(delta.getRows())`
     */
    fun withRelative(delta: Size): Size {
        return withRelativeRows(delta.rows).withRelativeColumns(delta.columns)
    }

    /**
     * Takes a different [Size] and returns a new [Size] that has the largest dimensions of the two,
     * measured separately. So calling 3x5 on a 5x3 will return 5x5.
     */
    fun max(other: Size): Size {
        return withColumns(Math.max(columns, other.columns))
                .withRows(Math.max(rows, other.rows))
    }

    /**
     * Takes a different [Size] and returns a new [Size] that has the smallest dimensions of the two,
     * measured separately. So calling 3x5 on a 5x3 will return 3x3.
     */
    fun min(other: Size): Size {
        return withColumns(Math.min(columns, other.columns))
                .withRows(Math.min(rows, other.rows))
    }

    /**
     * Returns itself if it is equal to the supplied size, otherwise the supplied size. You can use this if you have a
     * size field which is frequently recalculated but often resolves to the same size; it will keep the same object
     * in memory instead of swapping it out every cycle.
     */
    fun with(size: Size): Size {
        if (equals(size)) {
            return this
        }
        return size
    }

    /**
     * Tells whether `size` fits within this [Size]
     * meaning that nor the `columns`, nor the `rows` of
     * `size` is bigger compared to the values of this [Size].
     */
    fun fitsWithin(size: Size): Boolean {
        return size.columns <= columns && size.rows <= rows
    }

    private fun returnZeroIfZero(size: Size): Size {
        return if (size.columns == 0 || size.rows == 0) {
            ZERO
        } else {
            size
        }
    }

    companion object {
        @JvmField
        val UNKNOWN = Size(Int.MAX_VALUE, Int.MAX_VALUE)

        @JvmField
        val DEFAULT = Size(80, 24)

        @JvmField
        val ZERO = Size(0, 0)

        @JvmField
        val ONE = Size(1, 1)

        /**
         * Factory method for [Size].
         */
        @JvmStatic
        fun of(columns: Int, rows: Int) = Size(columns, rows)
    }
}
