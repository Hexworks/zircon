package org.codetome.zircon.event

enum class EventType {
    /**
     * A redraw should be performed.
     */
    DRAW,
    /**
     * A new input arrived into the system.
     */
    INPUT,
    /**
     * A mouse action happened.
     */
    MOUSE_ACTION,
    /**
     * A component was hovered over.
     */
    HOVER,
    /**
     * A [org.codetome.zircon.screen.Screen] has been switched to
     * (eg: the `display` function has been called on a Screen object).
     */
    SCREEN_SWITCH,
    /**
     * A component changed on a screen.
     */
    COMPONENT_CHANGE
}