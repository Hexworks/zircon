package org.hexworks.zircon.api.uievent

import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.data.ComponentState

/**
 * An [ComponentEventSource] is an object which emits [ComponentEvent]s and can be used to listen to
 * those events. Note that [ComponentEvent]s don't support [UIEventPhase]s.
 */
interface ComponentEventSource {

    /**
     * Adds the given [handler] for [ComponentEvent]s. Use this if you selectively
     * respond to events.
     */
    fun handleComponentEvents(
        eventType: ComponentEventType,
        handler: (event: ComponentEvent) -> UIEventResponse
    ): Subscription

    /**
     * Adds the given [handler] for [ComponentEvent]s. Use this if you handle all
     * events.
     */
    fun processComponentEvents(
        eventType: ComponentEventType,
        handler: (event: ComponentEvent) -> Unit
    ): Subscription

    /**
     * Adds a listener to this [Component] that will be called whenever
     * this [Component] is activated.
     * @see ComponentState.ACTIVE
     * @return a [Subscription] that can be used to cancel this listener
     */
    fun onActivated(fn: (ComponentEvent) -> Unit): Subscription {
        return processComponentEvents(ComponentEventType.ACTIVATED, fn)
    }

    /**
     * Adds a listener to this [Component] that will be called whenever
     * this [Component] is deactivated.
     * @see ComponentState.DEFAULT
     * @see ComponentState.FOCUSED
     * @return a [Subscription] that can be used to cancel this listener
     */
    fun onDeactivated(fn: (ComponentEvent) -> Unit): Subscription {
        return processComponentEvents(ComponentEventType.DEACTIVATED, fn)
    }

    /**
     * Adds a listener to this [Component] that will be called whenever
     * this [Component] gains focus.
     * @see ComponentState.FOCUSED
     * @see Component.hasFocus
     * @return a [Subscription] that can be used to cancel this listener
     */
    fun onFocusGiven(fn: (ComponentEvent) -> Unit): Subscription {
        return processComponentEvents(ComponentEventType.FOCUS_GIVEN, fn)
    }

    /**
     * Adds a listener to this [Component] that will be called whenever
     * this [Component] loses focus.
     * @see ComponentState.FOCUSED
     * @see Component.hasFocus
     * @return a [Subscription] that can be used to cancel this listener
     */
    fun onFocusTaken(fn: (ComponentEvent) -> Unit): Subscription {
        return processComponentEvents(ComponentEventType.FOCUS_TAKEN, fn)
    }


}
