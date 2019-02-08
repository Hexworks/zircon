package org.hexworks.zircon.api.uievent

/**
 * Handler for [MouseEvent]s.
 */
interface MouseEventHandler : UIEventHandler<MouseEvent> {

    override fun handle(event: MouseEvent, phase: UIEventPhase): UIEventResponse
}
