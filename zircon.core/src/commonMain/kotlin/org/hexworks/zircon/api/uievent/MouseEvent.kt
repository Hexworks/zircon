package org.hexworks.zircon.api.uievent

import org.hexworks.zircon.api.data.Position

data class MouseEvent(
        override val type: MouseEventType,
        val button: Int,
        val position: Position
) : UIEvent