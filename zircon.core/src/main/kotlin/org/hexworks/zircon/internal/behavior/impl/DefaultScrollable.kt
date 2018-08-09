package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.zircon.api.behavior.Scrollable
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.util.Math

class DefaultScrollable(private var visibleSize: Size,
                        private var actualSize: Size) : Scrollable {

    private var offset = Position.defaultPosition()

    init {
        checkSizes()
    }

    override fun actualSize() = actualSize

    override fun setActualSize(size: Size) {
        checkSizes()
        this.actualSize = size
    }

    override fun visibleSize() = visibleSize

    override fun visibleOffset() = offset

    override fun scrollOneRight(): Position {
        if (visibleSize.xLength + offset.x < actualSize.xLength) {
            this.offset = offset.withRelativeX(1)
        }
        return offset
    }

    override fun scrollOneDown(): Position {
        if (visibleSize.yLength + offset.y < actualSize.yLength) {
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
        val lastScrollableColumn = actualSize.xLength - visibleSize.xLength
        offset = offset.withX(Math.min(columnToScrollTo, lastScrollableColumn))
        return offset
    }

    override fun scrollLeftBy(columns: Int): Position {
        require(columns >= 0) {
            "You can only scroll left by a positive amount!"
        }
        val columnToScrollTo = offset.x - columns
        offset = offset.withX(Math.max(0, columnToScrollTo))
        return offset
    }

    override fun scrollUpBy(rows: Int): Position {
        require(rows >= 0) {
            "You can only scroll up by a positive amount!"
        }
        val rowToScrollTo = offset.y - rows
        offset = offset.withY(Math.max(0, rowToScrollTo))
        return offset
    }

    override fun scrollDownBy(rows: Int): Position {
        require(rows >= 0) {
            "You can only scroll down by a positive amount!"
        }
        val rowToScrollTo = offset.y + rows
        val lastScrollableRow = actualSize.yLength - visibleSize.yLength
        offset = offset.withY(Math.min(rowToScrollTo, lastScrollableRow))
        return offset
    }

    private fun checkSizes() {
        require(actualSize.xLength >= visibleSize.xLength) {
            "Can't have a virtual space (${actualSize.xLength}, ${actualSize.yLength})" +
                    " with less xLength than the visible space (${visibleSize.xLength}, ${visibleSize.yLength})!"
        }
        require(actualSize.yLength >= visibleSize.yLength) {
            "Can't have a virtual space (${actualSize.xLength}, ${actualSize.yLength})" +
                    " with less yLength than the visible space (${visibleSize.xLength}, ${visibleSize.yLength})!"
        }
    }
}
