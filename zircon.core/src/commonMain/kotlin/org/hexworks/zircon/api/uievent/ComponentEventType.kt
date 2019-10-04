package org.hexworks.zircon.api.uievent

/**
 * Event type for the different kinds of component evens.
 */
enum class ComponentEventType : UIEventType {

    /**
     * The component was given focus.
     */
    FOCUS_GIVEN,
    /**
     * The component's focus was taken away.
     */
    FOCUS_TAKEN,
    /**
     * The component was activated (eg: mouse click, or spacebar press by default).
     */
    ACTIVATED
}
