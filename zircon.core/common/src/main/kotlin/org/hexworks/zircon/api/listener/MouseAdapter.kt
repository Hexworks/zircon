package org.hexworks.zircon.api.listener

import org.hexworks.zircon.api.input.MouseAction

/**
 * Adapter for [MouseListener] which has empty default
 * implementations.
 */
open class MouseAdapter : MouseListener {

    /**
     * A mouse button has been clicked (pressed
     * and released) on a component.
     */
    override fun mouseClicked(action: MouseAction) {}

    /**
     * A mouse button has been pressed on a component.
     */
    override fun mousePressed(action: MouseAction) {}

    /**
     * A mouse button has been released on a component.
     */
    override fun mouseReleased(action: MouseAction) {}

    /**
     * The mouse entered a component.
     */
    override fun mouseEntered(action: MouseAction) {}

    /**
     * The mouse exited a component.
     */
    override fun mouseExited(action: MouseAction) {}

    /**
     * the mouse wheel is rotated up (away from user)
     */
    override fun mouseWheelRotatedUp(action: MouseAction) {}

    /**
     * the mouse wheel is rotated down (towards user)
     */
    override fun mouseWheelRotatedDown(action: MouseAction) {}

    /**
     * A mouse button is pressed on a component and then dragged.
     */
    override fun mouseDragged(action: MouseAction) {}

    /**
     * The mouse cursor has been moved onto a component but no buttons have been pushed.
     */
    override fun mouseMoved(action: MouseAction) {}
}
