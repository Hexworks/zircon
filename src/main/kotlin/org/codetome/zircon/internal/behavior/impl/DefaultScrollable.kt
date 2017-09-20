package org.codetome.zircon.internal.behavior.impl

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.behavior.CursorHandler
import org.codetome.zircon.internal.behavior.Scrollable

class DefaultScrollable(cursorSpaceSize: Size,
                        private var virtualSpaceSize: Size,
                        private val cursorHandler: CursorHandler = DefaultCursorHandler(cursorSpaceSize))
    : Scrollable, CursorHandler by cursorHandler {

    private var offset = Position.DEFAULT_POSITION

    override fun getVirtualSpaceSize() = virtualSpaceSize

    override fun setVirtualSpaceSize(size: Size) {
        this.virtualSpaceSize = size
    }

    override fun getVisibleOffset() = offset

    override fun scrollOneRight() {
        if (getCursorSpaceSize().columns + offset.column + 1 <= virtualSpaceSize.columns) {
            this.offset = offset.withRelativeColumn(1)
        }
    }

    override fun scrollRightTo(position: Int) {
        if (position in 0..virtualSpaceSize.columns) {
            offset = offset.withColumn(position)
        }
    }

    override fun scrollOneLeft() {
        if (offset.column > 0) {
            offset = offset.withRelativeColumn(-1)
        }
    }

    override fun scrollLeftTo(position: Int) {
        if (position in 0..offset.column) {
            offset = offset.withColumn(position)
        }
    }

    override fun scrollOneUp() {
        if (offset.row > 0) {
            offset = offset.withRelativeRow(-1)
        }
    }

    override fun scrollOneDown() {
        if (getCursorSpaceSize().rows + offset.row + 1 <= virtualSpaceSize.rows) {
            this.offset = offset.withRelativeRow(1)
        }
    }
}