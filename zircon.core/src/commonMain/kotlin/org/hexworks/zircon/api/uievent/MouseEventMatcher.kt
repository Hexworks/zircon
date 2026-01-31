package org.hexworks.zircon.api.uievent

import org.hexworks.zircon.api.data.Position

data class MouseEventMatcher(
    val type: MouseEventType? = null,
    val button: Int? = null,
    val position: Position? = null
) {

    fun matches(event: MouseEvent): Boolean {
        var result = true
        type?.let {
            result = result && it == event.type
        }
        button?.let {
            result = result && it == event.button
        }
        position?.let {
            result = result && it == event.position
        }
        return result
    }

    companion object {

        fun create(
            type: MouseEventType? = null,
            button: Int? = null,
            position: Position? = null
        ) = MouseEventMatcher(
            type = type,
            button = button,
            position = position
        )
    }

}
