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
    var cursorSpaceSize: Size
}
