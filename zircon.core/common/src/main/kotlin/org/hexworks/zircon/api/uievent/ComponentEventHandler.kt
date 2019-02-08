package org.hexworks.zircon.api.uievent

/**
 * Handler for [ComponentEvent]s.
 */
interface ComponentEventHandler {

    /**
     * Handles the given [ComponentEvent].
     */
    fun handle(event: ComponentEvent): UIEventResponse
}
