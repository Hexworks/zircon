package org.hexworks.zircon.api.uievent

import org.hexworks.cobalt.events.api.Event

/**
 * An [UIEvent] represents an event which happens on the UI and supports
 * event propagation (see [UIEventPhase]).
 */
//! TODO: why don't we implement Event here?
interface UIEvent {

    val type: UIEventType

}
