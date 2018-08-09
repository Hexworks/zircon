package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.util.Math
import org.hexworks.zircon.internal.behavior.InternalCursorHandler

class DefaultCursorHandler(private var cursorSpace: Size)
    : InternalCursorHandler {

    private var cursorPosition = Position.defaultPosition()
    private var cursorVisible = false

    override fun cursorPosition(): Position = cursorPosition

    override fun putCursorAt(cursorPosition: Position): Boolean {
        val newCursorPos = cursorPosition
                .withX(Math.min(cursorPosition.x, cursorSpace.xLength - 1))
                .withY(Math.min(cursorPosition.y, cursorSpace.yLength - 1))
        return if (this.cursorPosition == newCursorPos) {
            false
        } else {
            this.cursorPosition = newCursorPos
            true
        }
    }

    override fun moveCursorForward() =
            putCursorAt(cursorPosition().let { (column) ->
                if (cursorIsAtTheEndOfTheLine(column)) {
                    cursorPosition().withX(0).withRelativeY(1)
                } else {
                    cursorPosition().withRelativeX(1)
                }
            })

    override fun moveCursorBackward() =
            putCursorAt(cursorPosition().let { (column) ->
                if (cursorIsAtTheStartOfTheLine(column)) {
                    if (cursorPosition().y > 0) {
                        cursorPosition().withX(cursorSpace.xLength - 1).withRelativeY(-1)
                    } else {
                        cursorPosition()
                    }
                } else {
                    cursorPosition().withRelativeX(-1)
                }
            })

    override fun isCursorVisible() = cursorVisible

    override fun isCursorAtTheEndOfTheLine() = cursorPosition.x == cursorSpace.xLength - 1

    override fun isCursorAtTheStartOfTheLine() = cursorPosition.x == 0

    override fun isCursorAtTheFirstRow() = cursorPosition.y == 0

    override fun isCursorAtTheLastRow() = cursorPosition.y == cursorSpace.yLength - 1

    override fun setCursorVisibility(cursorVisible: Boolean): Boolean {
        return if (this.cursorVisible == cursorVisible) {
            false
        } else {
            this.cursorVisible = cursorVisible
            true
        }
    }

    override fun getCursorSpaceSize() = cursorSpace

    override fun resizeCursorSpace(size: Size) {
        this.cursorSpace = size
        putCursorAt(cursorPosition())
    }

    private fun cursorIsAtTheEndOfTheLine(column: Int) = column + 1 == cursorSpace.xLength

    private fun cursorIsAtTheStartOfTheLine(column: Int) = column == 0

}
