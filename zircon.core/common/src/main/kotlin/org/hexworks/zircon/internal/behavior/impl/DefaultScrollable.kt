package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.zircon.api.behavior.Scrollable
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import kotlin.math.max
import kotlin.math.min

class DefaultScrollable(override val visibleSize: Size,
                        initialActualSize: Size) : Scrollable {


    override var actualSize: Size = initialActualSize
        set(value) {
            checkSizes()
            field = value
        }

    override val visibleOffset: Position
        get() = offset

    private var offset = Position.defaultPosition()

    init {
        checkSizes()
    }

    override fun scrollOneRight(): Position {
        if (visibleSize.width + offset.x < actualSize.width) {
            this.offset = offset.withRelativeX(1)
        }
        return offset
    }

    override fun scrollOneDown(): Position {
        if (visibleSize.height + offset.y < actualSize.height) {
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
        val lastScrollableColumn = actualSize.width - visibleSize.width
        offset = offset.withX(min(columnToScrollTo, lastScrollableColumn))
        return offset
    }

    override fun scrollLeftBy(columns: Int): Position {
        require(columns >= 0) {
            "You can only scroll left by a positive amount!"
        }
        val columnToScrollTo = offset.x - columns
        offset = offset.withX(max(0, columnToScrollTo))
        return offset
    }

    override fun scrollUpBy(rows: Int): Position {
        require(rows >= 0) {
            "You can only scroll up by a positive amount!"
        }
        val rowToScrollTo = offset.y - rows
        offset = offset.withY(max(0, rowToScrollTo))
        return offset
    }

    override fun scrollDownBy(rows: Int): Position {
        require(rows >= 0) {
            "You can only scroll down by a positive amount!"
        }
        val rowToScrollTo = offset.y + rows
        val lastScrollableRow = actualSize.height - visibleSize.height
        offset = offset.withY(min(rowToScrollTo, lastScrollableRow))
        return offset
    }

    private fun checkSizes() {
        require(actualSize.width >= visibleSize.width) {
            "Can't have a virtual space (${actualSize.width}, ${actualSize.height})" +
                    " with less xLength than the visible space (${visibleSize.width}, ${visibleSize.height})!"
        }
        require(actualSize.height >= visibleSize.height) {
            "Can't have a virtual space (${actualSize.width}, ${actualSize.height})" +
                    " with less yLength than the visible space (${visibleSize.width}, ${visibleSize.height})!"
        }
    }
}
