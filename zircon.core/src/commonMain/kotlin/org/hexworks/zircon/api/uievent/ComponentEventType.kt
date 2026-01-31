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
     * The component was activated (e.g.: mouse click, or space bar press by default).
     */
    ACTIVATED,

    /**
     * The component was deactivated (e.g.: mouse release, or space bar release by default).
     */
    DEACTIVATED
}
