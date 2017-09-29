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
     * Moves the cursor to a new location on this [CursorHandler].
     * Does nothing if the cursor is already at the given position.
     * @return true if the cursor was changed false if not.
     */
    fun putCursorAt(cursorPosition: Position): Boolean

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

    fun isCursorVisible(): Boolean

    fun isCursorAtTheEndOfTheLine(): Boolean

    fun isCursorAtTheStartOfTheLine(): Boolean

    fun isCursorAtTheFirstRow(): Boolean

    fun isCursorAtTheLastRow(): Boolean

    fun setCursorVisibility(cursorVisible: Boolean)

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