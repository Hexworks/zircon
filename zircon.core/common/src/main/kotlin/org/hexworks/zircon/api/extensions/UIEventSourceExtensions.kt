package org.hexworks.zircon.api.extensions

import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.uievent.ComponentEvent
import org.hexworks.zircon.api.uievent.ComponentEventHandler
import org.hexworks.zircon.api.uievent.ComponentEventProcessor
import org.hexworks.zircon.api.uievent.ComponentEventSource
import org.hexworks.zircon.api.uievent.ComponentEventType
import org.hexworks.zircon.api.uievent.KeyboardEvent
import org.hexworks.zircon.api.uievent.KeyboardEventHandler
import org.hexworks.zircon.api.uievent.KeyboardEventProcessor
import org.hexworks.zircon.api.uievent.KeyboardEventType
import org.hexworks.zircon.api.uievent.MouseEvent
import org.hexworks.zircon.api.uievent.MouseEventHandler
import org.hexworks.zircon.api.uievent.MouseEventProcessor
import org.hexworks.zircon.api.uievent.MouseEventType
import org.hexworks.zircon.api.uievent.UIEventPhase
import org.hexworks.zircon.api.uievent.UIEventResponse
import org.hexworks.zircon.api.uievent.UIEventSource

inline fun UIEventSource.handleMouseEvents(eventType: MouseEventType, crossinline fn: (MouseEvent, UIEventPhase) -> UIEventResponse): Subscription {
    return handleMouseEvents(eventType, object : MouseEventHandler {
        override fun handle(event: MouseEvent, phase: UIEventPhase): UIEventResponse {
            return fn(event, phase)
        }
    })
}

inline fun UIEventSource.processMouseEvents(eventType: MouseEventType, crossinline fn: (MouseEvent, UIEventPhase) -> Unit): Subscription {
    return processMouseEvents(eventType, object : MouseEventProcessor {
        override fun process(event: MouseEvent, phase: UIEventPhase) {
            fn(event, phase)
        }
    })
}

inline fun UIEventSource.handleKeyboardEvents(eventType: KeyboardEventType, crossinline fn: (KeyboardEvent, UIEventPhase) -> UIEventResponse): Subscription {
    return handleKeyboardEvents(eventType, object : KeyboardEventHandler {
        override fun handle(event: KeyboardEvent, phase: UIEventPhase): UIEventResponse {
            return fn(event, phase)
        }
    })
}

inline fun UIEventSource.processKeyboardEvents(eventType: KeyboardEventType, crossinline fn: (KeyboardEvent, UIEventPhase) -> Unit): Subscription {
    return processKeyboardEvents(eventType, object : KeyboardEventProcessor {
        override fun process(event: KeyboardEvent, phase: UIEventPhase) {
            fn(event, phase)
        }
    })
}

inline fun ComponentEventSource.handleComponentEvents(eventType: ComponentEventType, crossinline fn: (ComponentEvent) -> UIEventResponse): Subscription {
    return handleComponentEvents(eventType, object : ComponentEventHandler {
        override fun handle(event: ComponentEvent): UIEventResponse {
            return fn(event)
        }
    })
}

inline fun ComponentEventSource.processComponentEvents(eventType: ComponentEventType, crossinline fn: (ComponentEvent) -> Unit): Subscription {
    return processComponentEvents(eventType, object : ComponentEventProcessor {
        override fun process(event: ComponentEvent) {
            fn(event)
        }
    })
}

@Deprecated(
        "use UIEventSource.handleMouseEvents",
        replaceWith = ReplaceWith(
                "handleMouseEvents(eventType, fn)",
                "org.hexworks.zircon.api.extensions.handleMouseEvents"))
inline fun UIEventSource.onMouseEvent(eventType: MouseEventType, crossinline fn: (MouseEvent, UIEventPhase) -> UIEventResponse): Subscription {
    return handleMouseEvents(eventType, fn)
}

@Deprecated(
        "use UIEventSource.handleKeyboardEvents",
        replaceWith = ReplaceWith(
                "handleKeyboardEvents(eventType, fn)",
                "org.hexworks.zircon.api.extensions.handleKeyboardEvents"))
inline fun UIEventSource.onKeyboardEvent(eventType: KeyboardEventType, crossinline fn: (KeyboardEvent, UIEventPhase) -> UIEventResponse): Subscription {
    return handleKeyboardEvents(eventType, fn)
}

@Deprecated(
        "use UIEventSource.handleComponentEvents",
        replaceWith = ReplaceWith(
                "handleComponentEvents(eventType, fn)",
                "org.hexworks.zircon.api.extensions.handleComponentEvents"))
inline fun ComponentEventSource.onComponentEvent(eventType: ComponentEventType, crossinline fn: (ComponentEvent) -> UIEventResponse): Subscription {
    return handleComponentEvents(eventType, fn)
}

