package org.hexworks.zircon.internal.uievent

import org.hexworks.zircon.api.uievent.*

/**
 * Callback interface for [ComponentEvent]s which can typically be implemented
 * by long-lived objects. See [ComponentEventType]. This class is part of the internal
 * API so don't call any of these methods, it is the library's job to do so.
 */
interface ComponentEventAdapter {

    /**
     * Focus was given to the component. Note that if focus was programmatically
     * given there is no originating [UIEvent].
     */
    fun focusGiven(): UIEventResponse = Pass

    /**
     * Focus was taken away from the component. Note that if focus was programmatically
     * taken there is no originating [UIEvent].
     */
    fun focusTaken(): UIEventResponse = Pass

    /**
     * The component was activated (mouse click or spacebar press typically).
     */
    fun activated(): UIEventResponse = Pass

    /**
     * The component was deactivated (mouse release or spacebar release typically).
     */
    fun deactivated(): UIEventResponse = Pass
}
