package org.codetome.zircon.internal.behavior.impl

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.internal.behavior.Dirtiable
import org.codetome.zircon.internal.behavior.InternalCursorHandler
import org.codetome.zircon.internal.multiplatform.api.Math

class DefaultCursorHandler(private var cursorSpace: Size,
                           private val dirtiable: Dirtiable = DefaultDirtiable())
    : InternalCursorHandler, Dirtiable by dirtiable {

    private var cursorPosition = Position.defaultPosition()
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

    override fun setCursorVisibility(cursorVisible: Boolean) =
            if (this.cursorVisible == cursorVisible) {
                false
            } else {
                this.cursorVisible = cursorVisible
                true
            }

    override fun getCursorPosition(): Position = cursorPosition

    override fun putCursorAt(cursorPosition: Position): Boolean {
        val newCursorPos = cursorPosition
                .withX(Math.min(cursorPosition.x, cursorSpace.xLength - 1))
                .withY(Math.min(cursorPosition.y, cursorSpace.yLength - 1))
        return if (this.cursorPosition == newCursorPos) {
            false
        } else {
            this.cursorPosition = newCursorPos
            setPositionDirty(this.cursorPosition)
            true
        }
    }

    override fun moveCursorForward() =
            putCursorAt(getCursorPosition().let { (column) ->
                if (cursorIsAtTheEndOfTheLine(column)) {
                    getCursorPosition().withX(0).withRelativeY(1)
                } else {
                    getCursorPosition().withRelativeX(1)
                }
            })

    override fun moveCursorBackward() =
            putCursorAt(getCursorPosition().let { (column) ->
                if (cursorIsAtTheStartOfTheLine(column)) {
                    if (getCursorPosition().y > 0) {
                        getCursorPosition().withX(cursorSpace.xLength - 1).withRelativeY(-1)
                    } else {
                        getCursorPosition()
                    }
                } else {
                    getCursorPosition().withRelativeX(-1)
                }
            })

    override fun getCursorSpaceSize() = cursorSpace

    override fun resizeCursorSpace(size: Size) {
        this.cursorSpace = size
        putCursorAt(getCursorPosition())
    }

    override fun isCursorAtTheEndOfTheLine() = cursorPosition.x == cursorSpace.xLength - 1

    override fun isCursorAtTheStartOfTheLine() = cursorPosition.x == 0

    override fun isCursorAtTheFirstRow() = cursorPosition.y == 0

    override fun isCursorAtTheLastRow() = cursorPosition.y == cursorSpace.yLength - 1

    private fun cursorIsAtTheEndOfTheLine(column: Int) = column + 1 == cursorSpace.xLength

    private fun cursorIsAtTheStartOfTheLine(column: Int) = column == 0
}
