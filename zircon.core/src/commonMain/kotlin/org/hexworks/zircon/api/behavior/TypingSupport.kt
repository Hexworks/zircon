package org.hexworks.zircon.api.behavior

import org.hexworks.zircon.api.data.Tile

/**
 * A [TypingSupport] is a specialized [CursorHandler] that not only handles a cursor
 * but can simulate typing (e.g.: put a [Tile] at the current cursor position and advance the cursor).
 */
interface TypingSupport : CursorHandler {

    /**
     * Adds the [tile] at the current cursor location. The cursor will then move one column to the right.
     * If the end of the line is reached while putting tiles using this function, the cursor will move
     * to the beginning of the next line (if there is one).
     */
    fun putTile(tile: Tile)

}
