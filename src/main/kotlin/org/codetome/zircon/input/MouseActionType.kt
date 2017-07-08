package org.codetome.zircon.input

/**
 * Enum type for the different kinds of mouse actions supported
 */
enum class MouseActionType {
    /**
     * A mouse button has been clicked (pressed
     * and released) on a component.
     */
    MOUSE_CLICKED,
    /**
     * A mouse button has been pressed on a component.
     */
    MOUSE_PRESSED,
    /**
     * A mouse button has been released on a component.
     */
    MOUSE_RELEASED,
    /**
     * The mouse entered a component.
     */
    MOUSE_ENTERED,
    /**
     * The mouse exited a component.
     */
    MOUSE_EXITED,
    /**
     * the mouse wheel is rotated up (away from user)
     */
    MOUSE_WHEEL_ROTATED_UP,
    /**
     * the mouse wheel is rotated down (towards user)
     */
    MOUSE_WHEEL_ROTATED_DOWN,
    /**
     * A mouse button is pressed on a component and then dragged.
     */
    MOUSE_DRAGGED,
    /**
     * The mouse cursor has been moved onto a component but no buttons have been pushed.
     */
    MOUSE_MOVED
}