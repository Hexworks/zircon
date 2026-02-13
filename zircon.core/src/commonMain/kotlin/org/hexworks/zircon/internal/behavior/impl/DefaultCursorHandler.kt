package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.zircon.api.data.Position.Companion.ZERO
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.behavior.InternalCursorHandler
import kotlin.math.max
import kotlin.math.min
import org.hexworks.zircon.api.data.Position.Companion.create as position

class DefaultCursorHandler(initialCursorSpace: Size) : InternalCursorHandler {

    override var isCursorVisible = false

    override var cursorSpaceSize = initialCursorSpace
        set(value) {
            field = value
            this.cursorPosition = cursorPosition
        }

    override var cursorPosition = ZERO
        set(value) {
            field = position(
                x = max(0, min(value.x, cursorSpaceSize.width - 1)),
                y = max(0, min(value.y, cursorSpaceSize.height - 1))
            )
        }
}
