package org.hexworks.zircon.api.uievent

import org.hexworks.cobalt.events.api.Subscription

/**
 * An [ComponentEventSource] is an object which emits [UIEvent]s and can be used to listen to
 * those events. Note that [ComponentEvent]s don't support [UIEventPhase]s
 */
interface ComponentEventSource {

    /**
     * Adds a handler for [ComponentEvent]s.
     */
    fun onComponentEvent(eventType: ComponentEventType, handler: ComponentEventHandler): Subscription
}
