package org.codetome.zircon.api.behavior

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size

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
     * Moves the title cursor to a new location on this [CursorHandler].
     */
    fun putCursorAt(cursorPosition: Position)

    /**
     * Moves the cursor one [Position] to the right. If the [Position] would be out
     * of bound regards to columns, the cursor will be moved the the 0th position
     * in the next row or left where it was if there is no next row.
     */
    fun moveCursorForward()

    /**
     * Moves the cursor one [Position] to the left. If the [Position] would be out
     * of bound regards to columns, the cursor will be moved the the last position
     * in the previous row or left where it was if there is no previous row.
     */
    fun moveCursorBackward()

    /**
     * Tells whether the cursor is visible or not.
     */
    fun isCursorVisible(): Boolean

    /**
     * Hides or shows the cursor.
     */
    fun setCursorVisible(cursorVisible: Boolean)

    /**
     * Returns the [Size] of the (virtual) space the cursor can occupy.
     */
    fun getCursorSpaceSize(): Size

    /**
     * Sets the 2d space which bounds the cursor.
     * For example in the case of a [org.codetome.zircon.api.terminal.Terminal]
     * it will be the terminal's size.
     */
    fun resizeCursorSpace(size: Size)
}