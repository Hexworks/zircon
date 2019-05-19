package org.hexworks.zircon.api.uievent

/**
 * Handler for [KeyboardEvent]s.
 */
interface KeyboardEventHandler {

    fun handle(event: KeyboardEvent, phase: UIEventPhase): UIEventResponse
}
