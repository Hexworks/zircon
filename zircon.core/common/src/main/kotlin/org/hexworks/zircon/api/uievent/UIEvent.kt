package org.hexworks.zircon.api.uievent

/**
 * An [UIEvent] represents an event which happens on the UI and supports
 * event propagation (see [UIEventPhase]).
 */
interface UIEvent {
    val type: UIEventType
}
