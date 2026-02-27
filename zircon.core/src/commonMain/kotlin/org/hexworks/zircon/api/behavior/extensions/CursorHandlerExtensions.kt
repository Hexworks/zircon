package org.hexworks.zircon.api.behavior.extensions

import org.hexworks.zircon.api.behavior.CursorHandler
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.extensions.withRelativeX
import org.hexworks.zircon.api.data.extensions.withRelativeY
import org.hexworks.zircon.api.data.extensions.withX

/**
 * Tells whether the cursor is at the end of the line.
 */
val CursorHandler.isCursorAtTheEndOfTheLine: Boolean
    get() = cursorPosition.x == cursorSpaceSize.width - 1

/**
 * Tells whether the cursor is at the start of the line.
 */
val CursorHandler.isCursorAtTheStartOfTheLine: Boolean
    get() = cursorPosition.x == 0

/**
 * Tells whether the cursor is at the first (index 0) row.
 */
val CursorHandler.isCursorAtTheFirstRow: Boolean
    get() = cursorPosition.y == 0

/**
 * Tells whether the cursor is at the last row.
 */
val CursorHandler.isCursorAtTheLastRow: Boolean
    get() = cursorPosition.y == cursorSpaceSize.height - 1

/**
 * Moves the cursor one [Position] to the right. If the [Position] would be out
 * of bound regards to columns, the cursor will be moved to the 0th position
 * in the next row, or it stays where it was if there is no next row.
 */
fun CursorHandler.moveCursorForward() {
    this.cursorPosition = cursorPosition.let { (column) ->
        if (cursorIsAtTheEndOfTheLine(column)) {
            cursorPosition.withX(0).withRelativeY(1)
        } else {
            cursorPosition.withRelativeX(1)
        }
    }
}

/**
 * Moves the cursor one [Position] to the left. If the [Position] would be out
 * of bounds regards to columns, the cursor will be moved to the last position
 * in the previous row, or it stays where it was if there is no previous row.
 */
fun CursorHandler.moveCursorBackward() {
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

private fun CursorHandler.cursorIsAtTheEndOfTheLine(column: Int) = column + 1 == cursorSpaceSize.width

private fun cursorIsAtTheStartOfTheLine(column: Int) = column == 0
