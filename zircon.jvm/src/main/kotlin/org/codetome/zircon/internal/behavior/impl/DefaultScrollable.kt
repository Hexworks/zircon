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
        if (visibleSpaceSize.xLength + offset.x < virtualSpaceSize.xLength) {
            this.offset = offset.withRelativeX(1)
        }
        return offset
    }

    override fun scrollOneDown(): Position {
        if (visibleSpaceSize.yLength + offset.y < virtualSpaceSize.yLength) {
            this.offset = offset.withRelativeY(1)
        }
        return offset
    }

    override fun scrollOneLeft(): Position {
        if (offset.x > 0) {
            offset = offset.withRelativeX(-1)
        }
        return offset
    }

    override fun scrollOneUp(): Position {
        if (offset.y > 0) {
            offset = offset.withRelativeY(-1)
        }
        return offset
    }

    override fun scrollRightBy(columns: Int): Position {
        require(columns >= 0) {
            "You can only scroll right by a positive amount!"
        }
        val columnToScrollTo = offset.x + columns
        val lastScrollableColumn = virtualSpaceSize.xLength - visibleSpaceSize.xLength
        offset = offset.copy(x = Math.min(columnToScrollTo, lastScrollableColumn))
        return offset
    }

    override fun scrollLeftBy(columns: Int): Position {
        require(columns >= 0) {
            "You can only scroll left by a positive amount!"
        }
        val columnToScrollTo = offset.x - columns
        offset = offset.copy(x = Math.max(0, columnToScrollTo))
        return offset
    }

    override fun scrollUpBy(rows: Int): Position {
        require(rows >= 0) {
            "You can only scroll up by a positive amount!"
        }
        val rowToScrollTo = offset.y - rows
        offset = offset.copy(y = Math.max(0, rowToScrollTo))
        return offset
    }

    override fun scrollDownBy(rows: Int): Position {
        require(rows >= 0) {
            "You can only scroll down by a positive amount!"
        }
        val rowToScrollTo = offset.y + rows
        val lastScrollableRow = virtualSpaceSize.yLength - visibleSpaceSize.yLength
        offset = offset.copy(y = Math.min(rowToScrollTo, lastScrollableRow))
        return offset
    }

    private fun checkSizes() {
        require(virtualSpaceSize.xLength >= visibleSpaceSize.xLength) {
            "Can't have a virtual space (${virtualSpaceSize.xLength}, ${virtualSpaceSize.yLength})" +
                    " with less xLength than the visible space (${visibleSpaceSize.xLength}, ${visibleSpaceSize.yLength})!"
        }
        require(virtualSpaceSize.yLength >= visibleSpaceSize.yLength) {
            "Can't have a virtual space (${virtualSpaceSize.xLength}, ${virtualSpaceSize.yLength})" +
                    " with less yLength than the visible space (${visibleSpaceSize.xLength}, ${visibleSpaceSize.yLength})!"
        }
    }
}
