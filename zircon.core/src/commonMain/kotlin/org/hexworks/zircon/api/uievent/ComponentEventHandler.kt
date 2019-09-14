package org.hexworks.zircon.api.uievent

/**
 * Handler for [ComponentEvent]s. Use [ComponentEventHandler]
 * if you listen to events, but don't use them all (you don't always
 * return [Processed]). If you handle all events consider using a
 * [ComponentEventProcessor] instead.
 */
interface ComponentEventHandler {

    /**
     * Handles the given [ComponentEvent].
     */
    fun handle(event: ComponentEvent): UIEventResponse
}
