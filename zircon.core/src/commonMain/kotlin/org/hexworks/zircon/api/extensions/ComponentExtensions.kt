package org.hexworks.zircon.api.extensions

import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.uievent.ComponentEvent
import org.hexworks.zircon.api.uievent.ComponentEventType.*

fun Component.onActivated(fn: (ComponentEvent) -> Unit): Subscription {
    return processComponentEvents(ACTIVATED, fn)
}

fun Component.onFocusGiven(fn: (ComponentEvent) -> Unit): Subscription {
    return processComponentEvents(FOCUS_GIVEN, fn)
}

fun Component.onFocusTaken(fn: (ComponentEvent) -> Unit): Subscription {
    return processComponentEvents(FOCUS_TAKEN, fn)
}