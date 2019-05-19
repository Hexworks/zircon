package org.hexworks.zircon.api.uievent

/**
 * Handler for [MouseEvent]s.
 */
interface MouseEventHandler {

    fun handle(event: MouseEvent, phase: UIEventPhase): UIEventResponse
}
