package org.hexworks.zircon.api.extensions

import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.uievent.*

inline fun UIEventSource.onMouseEvent(eventType: MouseEventType, crossinline fn: (MouseEvent, UIEventPhase) -> UIEventResponse): Subscription {
    return onMouseEvent(eventType, object : MouseEventHandler {
        override fun handle(event: MouseEvent, phase: UIEventPhase): UIEventResponse {
            return fn(event, phase)
        }
    })
}

inline fun UIEventSource.onKeyboardEvent(eventType: KeyboardEventType, crossinline fn: (KeyboardEvent, UIEventPhase) -> UIEventResponse): Subscription {
    return onKeyboardEvent(eventType, object : KeyboardEventHandler {
        override fun handle(event: KeyboardEvent, phase: UIEventPhase): UIEventResponse {
            return fn(event, phase)
        }
    })
}

inline fun ComponentEventSource.onComponentEvent(eventType: ComponentEventType, crossinline fn: (ComponentEvent) -> UIEventResponse): Subscription {
    return onComponentEvent(eventType, object : ComponentEventHandler {
        override fun handle(event: ComponentEvent): UIEventResponse {
            return fn(event)
        }
    })
}
