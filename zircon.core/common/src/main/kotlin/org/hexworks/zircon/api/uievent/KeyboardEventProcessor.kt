package org.hexworks.zircon.api.uievent

/**
 * Handler for [KeyboardEvent]s. Differs from a [KeyboardEventHandler]
 * in a way that its [process] method doesn't return
 * an [UIEventResponse], but [Processed] is returned implicitly to the framework.
 *
 * Use [KeyboardEventProcessor] if you **always** handle the event in [process]
 * (you never skip/drop events). This means that [KeyboardEventProcessor] can
 * be used if you find yourself always returning [Processed] form your event listeners.
 */
interface KeyboardEventProcessor {

    /**
     * Handles the given [event] in a given [phase].
     */
    fun process(event: KeyboardEvent, phase: UIEventPhase)
}
