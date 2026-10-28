package org.hexworks.zircon.internal.uievent

import org.hexworks.zircon.api.uievent.*

/**
 * Callback interface for [KeyboardEvent]s which can typically be implemented
 * by long-lived objects. See [KeyboardEventType]. This class is part of the internal
 * API so don't call any of these methods, it is the library's job to do so.
 */
public interface KeyboardEventAdapter {

    public fun keyPressed(event: KeyboardEvent, phase: UIEventPhase): UIEventResponse = Pass

    public fun keyTyped(event: KeyboardEvent, phase: UIEventPhase): UIEventResponse = Pass

    public fun keyReleased(event: KeyboardEvent, phase: UIEventPhase): UIEventResponse = Pass
}
