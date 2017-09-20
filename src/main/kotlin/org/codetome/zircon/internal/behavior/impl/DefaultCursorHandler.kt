package org.codetome.zircon.internal.behavior.impl

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.internal.behavior.Dirtiable
import org.codetome.zircon.internal.behavior.InternalCursorHandler

class DefaultCursorHandler(private var cursorSpace: Size,
                           private val dirtiable: Dirtiable = DefaultDirtiable())
    : InternalCursorHandler, Dirtiable by dirtiable {

    private var cursorPosition = Position.DEFAULT_POSITION
    private var cursorVisible = false

    init {
        setPositionDirty(cursorPosition)
    }

    override fun drainDirtyPositions(): Set<Position> {
        return dirtiable.drainDirtyPositions().also {
            setPositionDirty(cursorPosition)
        }
    }

    override fun isCursorVisible() = cursorVisible

    override fun setCursorVisible(cursorVisible: Boolean) {
        this.cursorVisible = cursorVisible
    }

    override fun getCursorPosition(): Position = cursorPosition

    override fun putCursorAt(cursorPosition: Position) {
        this.cursorPosition = cursorPosition
                .withColumn(Math.min(cursorPosition.column, cursorSpace.columns - 1))
                .withRow(Math.min(cursorPosition.row, cursorSpace.rows - 1))
        setPositionDirty(this.cursorPosition)
    }

    override fun moveCursorForward() {
        putCursorAt(getCursorPosition().let { (column) ->
            if (cursorIsAtTheEndOfTheLine(column)) {
                getCursorPosition().withColumn(0).withRelativeRow(1)
            } else {
                getCursorPosition().withRelativeColumn(1)
            }
        })
    }

    override fun moveCursorBackward() {
        putCursorAt(getCursorPosition().let { (column) ->
            if (cursorIsAtTheStartOfTheLine(column)) {
                if (getCursorPosition().row > 0) {
                    getCursorPosition().withColumn(cursorSpace.columns - 1).withRelativeRow(-1)
                } else {
                    getCursorPosition()
                }
            } else {
                getCursorPosition().withRelativeColumn(-1)
            }
        })
    }

    override fun getCursorSpaceSize() = cursorSpace

    override fun resizeCursorSpace(size: Size) {
        this.cursorSpace = size
        putCursorAt(getCursorPosition())
    }

    override fun isCursorAtTheEndOfTheLine() = cursorPosition.column == cursorSpace.columns - 1

    override fun isCursorAtTheStartOfTheLine() = cursorPosition.column == 0

    override fun isCursorAtTheFirstRow() = cursorPosition.row == 0

    override fun isCursorAtTheLastRow() = cursorPosition.row == cursorSpace.rows - 1

    private fun cursorIsAtTheEndOfTheLine(column: Int) = column + 1 == cursorSpace.columns

    private fun cursorIsAtTheStartOfTheLine(column: Int) = column == 0
}