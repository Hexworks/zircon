package org.hexworks.zircon.internal.uievent

import org.hexworks.zircon.api.uievent.*

/**
 * Callback interface for [KeyboardEvent]s which can typically be implemented
 * by long-lived objects. See [KeyboardEventType]. This class is part of the internal
 * API so don't call any of these methods, it is the library's job to do so.
 */
interface KeyboardEventAdapter {

    fun keyPressed(event: KeyboardEvent, phase: UIEventPhase): UIEventResponse = Pass

    fun keyTyped(event: KeyboardEvent, phase: UIEventPhase): UIEventResponse = Pass

    fun keyReleased(event: KeyboardEvent, phase: UIEventPhase): UIEventResponse = Pass
}
