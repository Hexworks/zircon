package org.hexworks.zircon.api.uievent

/**
 * Callback interface for handling [UIEvent]s.
 * @param T the type of the [UIEvent] which is handled.
 */
interface UIEventHandler<T: UIEvent> {

    /**
     * Handles the given [event] in a given [phase].
     * See [UIEventResponse] and [UIEventPhase] for more info.
     */
    fun handle(event: T, phase: UIEventPhase): UIEventResponse
}
