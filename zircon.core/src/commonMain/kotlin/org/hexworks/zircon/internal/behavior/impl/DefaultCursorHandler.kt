package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.behavior.InternalCursorHandler
import kotlin.math.min

class DefaultCursorHandler(initialCursorSpace: Size)
    : InternalCursorHandler {

    override var isCursorVisible = false

    override val isCursorAtTheEndOfTheLine: Boolean
        get() = cursorPosition.x == cursorSpaceSize.width - 1

    override val isCursorAtTheStartOfTheLine: Boolean
        get() = cursorPosition.x == 0

    override val isCursorAtTheFirstRow: Boolean
        get() = cursorPosition.y == 0

    override val isCursorAtTheLastRow: Boolean
        get() = cursorPosition.y == cursorSpaceSize.height - 1

    override var cursorSpaceSize = initialCursorSpace
        set(value) {
            field = value
            this.cursorPosition = cursorPosition
        }

    override var cursorPosition = Position.defaultPosition()
        set(value) {
            require(value.hasNegativeComponent.not()) {
                "Can't put the cursor at a negative position: $value"
            }
            field = cursorPosition
                    .withX(min(value.x, cursorSpaceSize.width - 1))
                    .withY(min(value.y, cursorSpaceSize.height - 1))
        }

    override fun moveCursorForward() {
        this.cursorPosition = cursorPosition.let { (column) ->
            if (cursorIsAtTheEndOfTheLine(column)) {
                cursorPosition.withX(0).withRelativeY(1)
            } else {
                cursorPosition.withRelativeX(1)
            }
        }
    }


    override fun moveCursorBackward() {
        this.cursorPosition = cursorPosition.let { (column) ->
            if (cursorIsAtTheStartOfTheLine(column)) {
                if (cursorPosition.y > 0) {
                    cursorPosition.withX(cursorSpaceSize.width - 1).withRelativeY(-1)
                } else {
                    cursorPosition
                }
            } else {
                cursorPosition.withRelativeX(-1)
            }
        }
    }

    private fun cursorIsAtTheEndOfTheLine(column: Int) = column + 1 == cursorSpaceSize.width

    private fun cursorIsAtTheStartOfTheLine(column: Int) = column == 0

}
