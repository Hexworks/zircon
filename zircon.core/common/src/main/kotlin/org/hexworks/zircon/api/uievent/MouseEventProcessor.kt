package org.hexworks.zircon.api.uievent

/**
 * Handler for [MouseEvent]s. Differs from a [MouseEventHandler]
 * in a way that its [MouseEventHandler.handle] method doesn't return
 * an [UIEventResponse], but [Processed] is returned implicitly to the framework.
 *
 * Use [MouseEventProcessor] if you **always** handle the event in [process]
 * (you never skip/drop events). This means that [MouseEventProcessor] can
 * be used if you find yourself always returning [Processed] form your event listeners.
 */
interface MouseEventProcessor {

    /**
     * Handles the given [event] in a given [phase].
     */
    fun process(event: MouseEvent, phase: UIEventPhase)
}
