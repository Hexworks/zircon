package org.codetome.zircon.internal.behavior.impl

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.internal.behavior.Scrollable

class DefaultScrollable(private var visibleSpaceSize: Size,
                        private var virtualSpaceSize: Size)
    : Scrollable {

    private var offset = Position.DEFAULT_POSITION

    init {
        checkSizes()
    }

    override fun getVirtualSpaceSize() = virtualSpaceSize

    override fun setVirtualSpaceSize(size: Size) {
        checkSizes()
        this.virtualSpaceSize = size
    }

    override fun getVisibleOffset() = offset

    override fun scrollOneRight(): Position {
        if (visibleSpaceSize.columns + offset.column < virtualSpaceSize.columns) {
            this.offset = offset.withRelativeColumn(1)
        }
        return offset
    }

    override fun scrollOneDown(): Position {
        if (visibleSpaceSize.rows + offset.row < virtualSpaceSize.rows) {
            this.offset = offset.withRelativeRow(1)
        }
        return offset
    }

    override fun scrollOneLeft(): Position {
        if (offset.column > 0) {
            offset = offset.withRelativeColumn(-1)
        }
        return offset
    }

    override fun scrollOneUp(): Position {
        if (offset.row > 0) {
            offset = offset.withRelativeRow(-1)
        }
        return offset
    }

    override fun scrollRightBy(columns: Int): Position {
        require(columns >= 0) {
            "You can only scroll right by a positive amount!"
        }
        val columnToScrollTo = offset.column + columns
        val lastScrollableColumn = virtualSpaceSize.columns - visibleSpaceSize.columns
        offset = offset.copy(column = Math.min(columnToScrollTo, lastScrollableColumn))
        return offset
    }

    override fun scrollLeftBy(columns: Int): Position {
        require(columns >= 0) {
            "You can only scroll left by a positive amount!"
        }
        val columnToScrollTo = offset.column - columns
        offset = offset.copy(column = Math.max(0, columnToScrollTo))
        return offset
    }

    override fun scrollUpBy(rows: Int): Position {
        require(rows >= 0) {
            "You can only scroll up by a positive amount!"
        }
        val rowToScrollTo = offset.row - rows
        offset = offset.copy(row = Math.max(0, rowToScrollTo))
        return offset
    }

    override fun scrollDownBy(rows: Int): Position {
        require(rows >= 0) {
            "You can only scroll down by a positive amount!"
        }
        val rowToScrollTo = offset.row + rows
        val lastScrollableRow = virtualSpaceSize.rows - visibleSpaceSize.rows
        offset = offset.copy(row = Math.min(rowToScrollTo, lastScrollableRow))
        return offset
    }

    private fun checkSizes() {
        require(virtualSpaceSize.columns >= visibleSpaceSize.columns) {
            "Can't have a virtual space (${virtualSpaceSize.columns}, ${virtualSpaceSize.rows})" +
                    " with less width than the visible space (${visibleSpaceSize.columns}, ${visibleSpaceSize.rows})!"
        }
        require(virtualSpaceSize.rows >= visibleSpaceSize.rows) {
            "Can't have a virtual space (${virtualSpaceSize.columns}, ${virtualSpaceSize.rows})" +
                    " with less depth than the visible space (${visibleSpaceSize.columns}, ${visibleSpaceSize.rows})!"
        }
    }
}