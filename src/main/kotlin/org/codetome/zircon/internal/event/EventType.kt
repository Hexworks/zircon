package org.codetome.zircon.internal.event

import java.util.*

sealed class EventType {
    /**
     * A redraw should be performed.
     */
    object Draw : EventType()
    /**
     * A new input arrived into the system.
     */
    object Input : EventType()

    data class MousePressed(val componentId: UUID) : EventType()

    data class MouseReleased(val componentId: UUID) : EventType()
    /**
     * A component was hovered over.
     */
    data class MouseOver(val componentId: UUID) : EventType()
    /**
     * A component is no longer hovered.
     */
    data class MouseOut(val componentId: UUID) : EventType()

    /**
     * Focus is requested for the given component.
     */
    object RequestFocusAt : EventType()

    /**
     * Cursor is requested at the given `position`.
     */
    object RequestCursorAt : EventType()
    /**
     * Hides the cursor
     */
    object HideCursor : EventType()
    /**
     * A [org.codetome.zircon.api.screen.Screen] has been switched to
     * (eg: the `display` function has been called on a Screen object).
     */
    object ScreenSwitch : EventType()
    /**
     * A component changed on a screen.
     */
    object ComponentChange : EventType()

    override fun toString(): String {
        return "EventType.${javaClass.simpleName}"
    }
}