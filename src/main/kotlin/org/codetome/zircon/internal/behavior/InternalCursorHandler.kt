package org.codetome.zircon.internal.behavior

import org.codetome.zircon.api.Size
import org.codetome.zircon.api.behavior.CursorHandler

/**
 * Represents the internal API of a [CursorHandler].
 */
interface InternalCursorHandler : CursorHandler, Dirtiable {

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