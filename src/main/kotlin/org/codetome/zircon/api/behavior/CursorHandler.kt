package org.codetome.zircon.api.behavior

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.internal.behavior.Dirtiable

interface CursorHandler : Dirtiable {

    /**
     * Returns the position of the cursor, as reported by the terminal.
     */
    fun getCursorPosition(): Position

    /**
     * Moves the title cursor to a new location on the terminal.
     */
    fun putCursorAt(cursorPosition: Position)

    /**
     * Moves the cursor one [Position] to the right. If the [Position] would be out
     * of bound regards to columns, the cursor will be moved the the 0th position
     * in the next row.
     */
    fun advanceCursor()

    /**
     * Checks if the terminal cursor is visible or not.
     */
    fun isCursorVisible(): Boolean

    /**
     * Hides or shows the title cursor.
     */
    fun setCursorVisible(cursorVisible: Boolean)

    /**
     * Sets the 2d space which bounds the cursor.
     * For example in the case of a [org.codetome.zircon.terminal.Terminal]
     * it will be the terminal's size.
     */
    fun resizeCursorSpace(size: Size)
}