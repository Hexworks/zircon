package org.hexworks.zircon.api.listener

import org.hexworks.zircon.api.input.MouseAction

/**
 * Listener interface for [MouseAction]s.
 */
interface MouseListener {

    /**
     * A mouse button has been clicked (pressed
     * and released) on a component.
     */
    fun mouseClicked(action: MouseAction) {}

    /**
     * A mouse button has been pressed on a component.
     */
    fun mousePressed(action: MouseAction) {}

    /**
     * A mouse button has been released on a component.
     */
    fun mouseReleased(action: MouseAction) {}

    /**
     * The mouse entered a component.
     */
    fun mouseEntered(action: MouseAction) {}

    /**
     * The mouse exited a component.
     */
    fun mouseExited(action: MouseAction) {}

    /**
     * the mouse wheel is rotated up (away from user)
     */
    fun mouseWheelRotatedUp(action: MouseAction) {}

    /**
     * the mouse wheel is rotated down (towards user)
     */
    fun mouseWheelRotatedDown(action: MouseAction) {}

    /**
     * A mouse button is pressed on a component and then dragged.
     */
    fun mouseDragged(action: MouseAction) {}
    /**
     * The mouse cursor has been moved onto a component but no buttons have been pushed.
     */
    fun mouseMoved(action: MouseAction) {}
}
