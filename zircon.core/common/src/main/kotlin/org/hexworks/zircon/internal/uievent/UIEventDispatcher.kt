package org.hexworks.zircon.internal.uievent

import org.hexworks.zircon.api.uievent.UIEvent
import org.hexworks.zircon.api.uievent.Pass
import org.hexworks.zircon.api.uievent.UIEventPhase
import org.hexworks.zircon.api.uievent.UIEventResponse
import org.hexworks.zircon.api.uievent.UIEventType

/**
 * A [UIEventDispatcher] is responsible for dispatching UI events for UI
 * components.
 */
interface UIEventDispatcher {


    /**
     * Dispatches the given [UIEvent] and propagates it throughout the UI controls
     * this [UIEventDispatcher] has. This will return [Pass] if there are no listeners
     * for the given [event]'s [UIEventType] or if invoking all listeners returned [Pass].
     * Otherwise [dispatch] will return the [UIEventResponse] with the highest precedence.
     */
    fun dispatch(event: UIEvent): UIEventResponse
}
