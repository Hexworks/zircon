package org.hexworks.zircon.api.uievent

import org.hexworks.cobalt.events.api.Subscription

/**
 * An [UIEventSource] is an object which emits [UIEvent]s and can be used to listen to
 * those events.
 */
interface UIEventSource {

    /**
     * Adds a handler for [MouseEvent]s.
     */
    fun onMouseEvent(eventType: MouseEventType, handler: MouseEventHandler): Subscription

    /**
     * Adds a handler for [KeyboardEvent]s.
     */
    fun onKeyboardEvent(eventType: KeyboardEventType, handler: KeyboardEventHandler): Subscription
}
