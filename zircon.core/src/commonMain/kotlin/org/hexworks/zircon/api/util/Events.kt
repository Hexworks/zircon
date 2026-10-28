package org.hexworks.zircon.api.util

import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEvent
import org.hexworks.zircon.api.uievent.KeyboardEventType

public fun KeyboardEvent.isNavigationKey(): Boolean =
    this == NAV_NEXT || this == NAV_PREV


public val NAV_NEXT: KeyboardEvent = KeyboardEvent(
    type = KeyboardEventType.KEY_RELEASED,
    key = "\t",
    code = KeyCode.TAB
)

public val NAV_PREV: KeyboardEvent = KeyboardEvent(
    type = KeyboardEventType.KEY_RELEASED,
    key = "\t",
    code = KeyCode.TAB,
    shiftDown = true
)