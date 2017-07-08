package org.codetome.zircon.terminal

import org.codetome.zircon.TerminalPosition

/**
 * Terminal dimensions in 2D space, measured in number of rows and columns.
 * This class is immutable and cannot change its internal state after creation.
 */
data class TerminalSize(val columns: Int,
                        val rows: Int) {

    init {
        require(columns >= 0) {
            "TerminalSize.columns cannot be less than 0!"
        }
        require(rows >= 0) {
            "TerminalSize.rows cannot be less than 0!"
        }
    }

    /**
     * Creates a list of [TerminalPosition]s in the order in which they should
     * be iterated when drawing (from left to right, then top to bottom).
     */
    fun fetchPositions(): List<TerminalPosition> = (0..rows - 1).flatMap { row ->
        (0..columns - 1).map { column ->
            TerminalPosition(column, row)
        }
    }

    /**
     * Creates a new size based on this size, but with a different width.
     */
    fun withColumns(columns: Int): TerminalSize {
        if (this.columns == columns) {
            return this
        }
        if (isZeroSize(columns)) return ZERO
        return TerminalSize(columns, this.rows)
    }

    /**
     * Creates a new size based on this size, but with a different height.
     */
    fun withRows(rows: Int): TerminalSize {
        if (this.rows == rows) {
            return this
        }
        if (isZeroSize(columns)) return ZERO
        return TerminalSize(this.columns, rows)
    }

    /**
     * Creates a new [TerminalSize] object representing a size with the same number of rows, but with a column size offset by a
     * supplied value. Calling this method with delta 0 will return this, calling it with a positive delta will return
     * a terminal size <code>delta</code> number of columns wider and for negative numbers shorter.
     */
    fun withRelativeColumns(delta: Int): TerminalSize {
        if (delta == 0) {
            return this
        }
        return withColumns(columns + delta)
    }

    /**
     * Creates a new [TerminalSize] object representing a size with the same number of columns, but with a row size offset by a
     * supplied value. Calling this method with delta 0 will return this, calling it with a positive delta will return
     * a terminal size <code>delta</code> number of rows longer and for negative numbers shorter.
     */
    fun withRelativeRows(delta: Int): TerminalSize {
        if (delta == 0) {
            return this
        }
        return withRows(rows + delta)
    }

    /**
     * Creates a new [TerminalSize] object representing a size based on this object's size but with a delta applied.
     * This is the same as calling `withRelativeColumns(delta.getColumns()).withRelativeRows(delta.getRows())`
     */
    fun withRelative(delta: TerminalSize): TerminalSize {
        return withRelativeRows(delta.rows).withRelativeColumns(delta.columns)
    }

    /**
     * Takes a different [TerminalSize] and returns a new [TerminalSize] that has the largest dimensions of the two,
     * measured separately. So calling 3x5 on a 5x3 will return 5x5.
     */
    fun max(other: TerminalSize): TerminalSize {
        return withColumns(Math.max(columns, other.columns))
                .withRows(Math.max(rows, other.rows))
    }

    /**
     * Takes a different [TerminalSize] and returns a new [TerminalSize] that has the smallest dimensions of the two,
     * measured separately. So calling 3x5 on a 5x3 will return 3x3.
     */
    fun min(other: TerminalSize): TerminalSize {
        return withColumns(Math.min(columns, other.columns))
                .withRows(Math.min(rows, other.rows))
    }

    /**
     * Returns itself if it is equal to the supplied size, otherwise the supplied size. You can use this if you have a
     * size field which is frequently recalculated but often resolves to the same size; it will keep the same object
     * in memory instead of swapping it out every cycle.
     */
    fun with(size: TerminalSize): TerminalSize {
        if (equals(size)) {
            return this
        }
        return size
    }

    private fun isZeroSize(columns: Int): Boolean {
        if (columns == 0 && this.rows == 0) {
            return true
        }
        return false
    }

    companion object {
        val UNKNOWN = TerminalSize(Int.MAX_VALUE, Int.MAX_VALUE)
        val DEFAULT = TerminalSize(80, 24)
        val ZERO = TerminalSize(0, 0)
        val ONE = TerminalSize(1, 1)
    }
}
