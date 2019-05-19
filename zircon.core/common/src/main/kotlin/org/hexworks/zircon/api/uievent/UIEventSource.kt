package org.hexworks.zircon.api.uievent

import org.hexworks.cobalt.events.api.Subscription

/**
 * An [UIEventSource] is an object which emits [UIEvent]s and can be used to listen to
 * those events.
 */
interface UIEventSource {

    /**
     * Adds a [MouseEventHandler] for mouse events. Use this if you selectively
     * consume events.
     */
    fun handleMouseEvents(eventType: MouseEventType, handler: MouseEventHandler): Subscription

    /**
     * Adds a [MouseEventProcessor] for mouse events. Use this if you handle all events.
     */
    fun processMouseEvents(eventType: MouseEventType, handler: MouseEventProcessor): Subscription

    /**
     * Adds a [KeyboardEventHandler] for mouse events. Use this if you selectively
     * consume events.
     */
    fun handleKeyboardEvents(eventType: KeyboardEventType, handler: KeyboardEventHandler): Subscription

    /**
     * Adds a [KeyboardEventProcessor] for mouse events. Use this if you handle all events.
     */
    fun processKeyboardEvents(eventType: KeyboardEventType, handler: KeyboardEventProcessor): Subscription
}
