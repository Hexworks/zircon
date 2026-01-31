package org.hexworks.zircon.api.util

import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEvent
import org.hexworks.zircon.api.uievent.KeyboardEventType

fun KeyboardEvent.isNavigationKey() =
    this == NAV_NEXT || this == NAV_PREV


val NAV_NEXT = KeyboardEvent(
    type = KeyboardEventType.KEY_RELEASED,
    key = "\t",
    code = KeyCode.TAB
)

val NAV_PREV = KeyboardEvent(
    type = KeyboardEventType.KEY_RELEASED,
    key = "\t",
    code = KeyCode.TAB,
    shiftDown = true
)