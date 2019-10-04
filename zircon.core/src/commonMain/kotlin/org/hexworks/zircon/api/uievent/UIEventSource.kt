package org.hexworks.zircon.api.uievent

import org.hexworks.cobalt.events.api.Subscription

/**
 * An [UIEventSource] is an object which emits [UIEvent]s and can be used to listen to
 * those events.
 */
interface UIEventSource {

    /**
     * Adds the given [handler] for mouse events. Use this if you selectively
     * consume events.
     */
    fun handleMouseEvents(
            eventType: MouseEventType,
            handler: (event: MouseEvent, phase: UIEventPhase) -> UIEventResponse): Subscription

    /**
     * Adds the given [handler] for [MouseEvent]s. Differs from a [handleMouseEvents]
     * in a way that its [handler] doesn't return an [UIEventResponse], but
     * [Processed] is returned implicitly to the framework.
     *
     * Use [processMouseEvents] if you **always** handle the event
     * (you never skip/drop events). This means that [processMouseEvents] can
     * be used if you find yourself always returning [Processed] form your event listeners.
     */
    fun processMouseEvents(
            eventType: MouseEventType,
            handler: (event: MouseEvent, phase: UIEventPhase) -> Unit): Subscription

    /**
     * Adds the given [handler] for keyboard events. Use this if you selectively
     * consume events.
     */
    fun handleKeyboardEvents(
            eventType: KeyboardEventType,
            handler: (event: KeyboardEvent, phase: UIEventPhase) -> UIEventResponse): Subscription

    /**
     * Adds the given [handler] for [KeyboardEvent]s. Differs from a [handleKeyboardEvents]
     * in a way that its [handler] doesn't return an [UIEventResponse], but
     * [Processed] is returned implicitly to the framework.
     *
     * Use [processKeyboardEvents] if you **always** handle the event
     * (you never skip/drop events). This means that [processKeyboardEvents] can
     * be used if you find yourself always returning [Processed] form your event listeners.
     */
    fun processKeyboardEvents(
            eventType: KeyboardEventType,
            handler: (event: KeyboardEvent, phase: UIEventPhase) -> Unit): Subscription
}
