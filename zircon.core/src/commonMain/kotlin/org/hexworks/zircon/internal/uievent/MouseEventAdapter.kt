package org.hexworks.zircon.internal.uievent

import org.hexworks.zircon.api.uievent.*

/**
 * Callback interface for [MouseEvent]s which can typically be implemented
 * by long-lived objects. See [MouseEventType]. This class is part of the internal
 * API so don't call any of these methods, it is the library's job to do so.
 */
public interface MouseEventAdapter {

    public fun mouseClicked(event: MouseEvent, phase: UIEventPhase): UIEventResponse = Pass

    public fun mousePressed(event: MouseEvent, phase: UIEventPhase): UIEventResponse = Pass

    public fun mouseReleased(event: MouseEvent, phase: UIEventPhase): UIEventResponse = Pass

    public fun mouseEntered(event: MouseEvent, phase: UIEventPhase): UIEventResponse = Pass

    public fun mouseExited(event: MouseEvent, phase: UIEventPhase): UIEventResponse = Pass

    public fun mouseWheelRotatedUp(event: MouseEvent, phase: UIEventPhase): UIEventResponse = Pass

    public fun mouseWheelRotatedDown(event: MouseEvent, phase: UIEventPhase): UIEventResponse = Pass

    public fun mouseDragged(event: MouseEvent, phase: UIEventPhase): UIEventResponse = Pass

    public fun mouseMoved(event: MouseEvent, phase: UIEventPhase): UIEventResponse = Pass
}
