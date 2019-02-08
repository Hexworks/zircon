package org.hexworks.zircon.api.uievent

/**
 * Handler for [KeyboardEvent]s.
 */
interface KeyboardEventHandler : UIEventHandler<KeyboardEvent> {

    override fun handle(event: KeyboardEvent, phase: UIEventPhase): UIEventResponse
}
