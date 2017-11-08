package org.codetome.zircon.internal.behavior.impl

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.internal.behavior.InternalCursorHandler
import org.codetome.zircon.internal.behavior.Scrollable

class DefaultScrollable(cursorSpaceSize: Size,
                        private var virtualSpaceSize: Size,
                        private val cursorHandler: InternalCursorHandler = DefaultCursorHandler(cursorSpaceSize))
    : Scrollable, InternalCursorHandler by cursorHandler {

    private var offset = Position.DEFAULT_POSITION

    override fun getVirtualSpaceSize() = virtualSpaceSize

    override fun setVirtualSpaceSize(size: Size) {
        this.virtualSpaceSize = size
    }

    override fun getVisibleOffset() = offset

    override fun scrollOneRight(): Position {
        if (getCursorSpaceSize().columns + offset.column + 1 <= virtualSpaceSize.columns) {
            this.offset = offset.withRelativeColumn(1)
        }
        return offset
    }

    override fun scrollRightTo(columns: Int): Position {
        if (columns in 0..virtualSpaceSize.columns) {
            offset = offset.withColumn(columns) // TODO: is this ok?
        }
        return offset
    }

    override fun scrollOneLeft(): Position {
        if (offset.column > 0) {
            offset = offset.withRelativeColumn(-1)
        }
        return offset
    }

    override fun scrollLeftTo(position: Int): Position {
        if (position in 0..offset.column) {
            offset = offset.withColumn(position)
        }
        return offset
    }

    override fun scrollOneUp(): Position {
        if (offset.row > 0) {
            offset = offset.withRelativeRow(-1)
        }
        return offset
    }

    override fun scrollOneDown(): Position {
        if (getCursorSpaceSize().rows + offset.row + 1 <= virtualSpaceSize.rows) {
            this.offset = offset.withRelativeRow(1)
        }
        return offset
    }
}