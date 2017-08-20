package org.codetome.zircon.behavior.impl

import org.codetome.zircon.Position
import org.codetome.zircon.Size
import org.codetome.zircon.behavior.CursorHandler
import org.codetome.zircon.behavior.Dirtiable

class DefaultCursorHandler private constructor(private var cursorSpace: Size,
                                               private val dirtiable: Dirtiable)
    : CursorHandler, Dirtiable by dirtiable {

    constructor(cursorSpace: Size) : this(
            cursorSpace = cursorSpace,
            dirtiable = DefaultDirtiable())

    private var cursorPosition = Position.DEFAULT_POSITION
    private var cursorVisible = true

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

    override fun advanceCursor() {
        putCursorAt(getCursorPosition().let { (column) ->
            if (cursorIsAtTheEndOfTheLine(column)) {
                getCursorPosition().withColumn(0).withRelativeRow(1)
            } else {
                getCursorPosition().withRelativeColumn(1)
            }
        })
    }

    override fun resizeCursorSpace(size: Size) {
        this.cursorSpace = size
        putCursorAt(getCursorPosition())
    }

    private fun cursorIsAtTheEndOfTheLine(column: Int) = column + 1 == cursorSpace.columns
}