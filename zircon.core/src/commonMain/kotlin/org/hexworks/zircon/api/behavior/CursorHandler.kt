package org.hexworks.zircon.api.behavior

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size

/**
 * Represents an object that manipulates a cursor. All operations that work with
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
     * Returns the [Size] of the (virtual) space the cursor can occupy.
     */
    val cursorSpaceSize: Size

}
