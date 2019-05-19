package org.hexworks.zircon.api.uievent

/**
 * Handler for [ComponentEvent]s. Differs from a [ComponentEventHandler]
 * in a way that its [ComponentEventProcessor.process] method doesn't return
 * an [UIEventResponse], but [Processed] is returned implicitly to the framework.
 *
 * Use [ComponentEventProcessor] if you **always** handle the event in [process]
 * (you never skip/drop events). This means that [ComponentEventProcessor] can
 * be used if you find yourself always returning [Processed] form your event listeners.
 */
interface ComponentEventProcessor {

    /**
     * Handles the given [ComponentEvent].
     */
    fun process(event: ComponentEvent)
}
