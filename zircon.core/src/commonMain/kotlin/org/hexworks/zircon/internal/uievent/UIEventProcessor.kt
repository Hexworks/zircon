package org.hexworks.zircon.internal.uievent

import org.hexworks.zircon.api.behavior.Closeable
import org.hexworks.zircon.api.uievent.*
import org.hexworks.zircon.internal.uievent.impl.DefaultUIEventProcessor

/**
 * An [UIEventProcessor] is responsible for processing [UIEvent]s
 * by dispatching them to its subscribers. An [UIEventProcessor]
 * will respect event propagation rules by checking the
 * [UIEventResponse]s of its listener. This means that for example if
 * one of the listeners returns [StopPropagation] it will stop invoking
 * the rest of the listeners.
 * Also see [UIEventSource].
 */
public interface UIEventProcessor : UIEventSource, Closeable {

    /**
     * Processes the given [UIEvent] in the given [phase].
     * @return `true` if the event was consumed and event
     * propagation should stop, `false` if not.
     */
    public fun process(event: UIEvent, phase: UIEventPhase): UIEventResponse

    public companion object {

        public fun createDefault(): DefaultUIEventProcessor = DefaultUIEventProcessor()
    }
}
