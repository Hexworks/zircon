package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Position.Companion.ZERO
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.behavior.InternalCursorHandler
import kotlin.math.max
import kotlin.math.min

public class DefaultCursorHandler(initialCursorSpace: Size) : InternalCursorHandler {

    override var isCursorVisible: Boolean = false

    override var cursorSpaceSize: Size = initialCursorSpace
        set(value) {
            field = value
            this.cursorPosition = cursorPosition
        }

    override var cursorPosition: Position = ZERO
        set(value) {
            field = Position(
                x = max(0, min(value.x, cursorSpaceSize.width - 1)),
                y = max(0, min(value.y, cursorSpaceSize.height - 1))
            )
        }
}
