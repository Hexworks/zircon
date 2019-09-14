package org.hexworks.zircon.internal.behavior

import org.hexworks.zircon.api.behavior.CursorHandler
import org.hexworks.zircon.api.data.Size

/**
 * Represents the internal API of a [CursorHandler].
 */
interface InternalCursorHandler : CursorHandler {

    /**
     * Returns the [Size] of the (virtual) space the cursor can occupy.
     */
    fun getCursorSpaceSize(): Size

    /**
     * Sets the 2d space which bounds the cursor.
     * For example in the case of a [org.hexworks.zircon.api.grid.TileGrid]
     * it will be the grid's size.
     */
    fun resizeCursorSpace(size: Size)
}
