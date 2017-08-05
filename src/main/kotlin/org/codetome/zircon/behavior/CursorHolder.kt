package org.codetome.zircon.behavior

import org.codetome.zircon.Position

interface CursorHolder {

    /**
     * Returns the position of the cursor, as reported by the terminal.
     */
    fun getCursorPosition(): Position

    /**
     * Moves the text cursor to a new location on the terminal.
     */
    fun setCursorPosition(cursorPosition: Position)

    /**
     * Checks if the terminal cursor is visible or not.
     */
    fun isCursorVisible(): Boolean

    /**
     * Hides or shows the text cursor.
     */
    fun setCursorVisible(cursorVisible: Boolean)
}