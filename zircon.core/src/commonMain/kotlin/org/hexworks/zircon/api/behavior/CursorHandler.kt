package org.hexworks.zircon.api.behavior

import org.hexworks.zircon.api.data.Position

/**
 * Represents an object which manipulates a cursor. All operations which work with
 * [Position]s are relative to the [CursorHandler]'s position.
 */
interface CursorHandler {

    /**
     * Tells whether the cursor is visible.
     */
    var isCursorVisible: Boolean

    /**
     * Returns the position of the cursor.
     */
    var cursorPosition: Position

    /**
     * Tells whether the cursor is at the end of the line.
     */
    val isCursorAtTheEndOfTheLine: Boolean

    /**
     * Tells whether the cursor is at the start of the line.
     */
    val isCursorAtTheStartOfTheLine: Boolean

    /**
     * Tells whether the cursor is at the first (index 0) row.
     */
    val isCursorAtTheFirstRow: Boolean

    /**
     * Tells whether the cursor is at the last row.
     */
    val isCursorAtTheLastRow: Boolean

    /**
     * Moves the cursor one [Position] to the right. If the [Position] would be out
     * of bound regards to columns, the cursor will be moved the the 0th position
     * in the next row or left where it was if there is no next row.
     */
    fun moveCursorForward()

    /**
     * Moves the cursor one [Position] to the left. If the [Position] would be out
     * of bounds regards to columns, the cursor will be moved the the last position
     * in the previous row or left where it was if there is no previous row.
     */
    fun moveCursorBackward()
}
