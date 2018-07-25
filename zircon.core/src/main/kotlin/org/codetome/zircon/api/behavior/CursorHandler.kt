package org.codetome.zircon.api.behavior

import org.codetome.zircon.api.Position

/**
 * Represents an object which manipulates a cursor (a [org.codetome.zircon.api.terminal.Terminal]
 * or a [org.codetome.zircon.api.component.TextBox] for example.
 * All operations which work with [Position]s are relative to the [CursorHandler]'s position!
 */
interface CursorHandler {

    /**
     * Returns the position of the cursor.
     */
    fun getCursorPosition(): Position

    /**
     * Moves the cursor to a new location on this [CursorHandler].
     * Does nothing if the cursor is already at the given position.
     * @return `true` if the cursor position changed `false` if not.
     */
    fun putCursorAt(cursorPosition: Position): Boolean

    /**
     * Moves the cursor one [Position] to the right. If the [Position] would be out
     * of bound regards to columns, the cursor will be moved the the 0th position
     * in the next row or left where it was if there is no next row.
     * @return `true` if the cursor position changed `false` if not.
     */
    fun moveCursorForward(): Boolean

    /**
     * Moves the cursor one [Position] to the left. If the [Position] would be out
     * of bounds regards to columns, the cursor will be moved the the last position
     * in the previous row or left where it was if there is no previous row.
     * @return `true` if the cursor position changed `false` if not.
     */
    fun moveCursorBackward(): Boolean

    /**
     * Tells whether the cursor is visible.
     */
    fun isCursorVisible(): Boolean

    /**
     * Tells whether the cursor is at the end of the line.
     */
    fun isCursorAtTheEndOfTheLine(): Boolean

    /**
     * Tells whether the cursor is at the start of the line.
     */
    fun isCursorAtTheStartOfTheLine(): Boolean

    /**
     * Tells whether the cursor is at the first (index 0) row.
     */
    fun isCursorAtTheFirstRow(): Boolean

    /**
     * Tells whether the cursor is at the last row.
     */
    fun isCursorAtTheLastRow(): Boolean

    /**
     * Sets the visibility of the cursor. `true` is visible, `false` is not.
     * @return `true` if change happened, `false` if not.
     */
    fun setCursorVisibility(cursorVisible: Boolean): Boolean
}
