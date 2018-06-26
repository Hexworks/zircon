package org.codetome.zircon.internal.event

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.input.KeyStroke
import org.codetome.zircon.api.input.MouseAction
import org.codetome.zircon.internal.util.Identifier

sealed class Event {
    /**
     * A new input arrived into the system.
     */
    data class Input(val input: org.codetome.zircon.api.input.Input) : Event()

    /**
     * A key was pressed.
     */
    data class KeyPressed(val keyStroke: KeyStroke) : Event()

    /**
     * A mouse button was pressed.
     */
    data class MousePressed(val mouseAction: MouseAction) : Event()

    /**
     * A mouse button was released.
     */
    data class MouseReleased(val mouseAction: MouseAction) : Event()

    /**
     * The mouse moved.
     */
    data class MouseMoved(val mouseAction: MouseAction) : Event()
    /**
     * A component was hovered over.
     */
    data class MouseOver(val mouseAction: MouseAction) : Event()

    /**
     * A component is no longer hovered.
     */
    data class MouseOut(val mouseAction: MouseAction) : Event()

    /**
     * Cursor is requested at the given `position`.
     */
    data class RequestCursorAt(val position: Position) : Event()

    /**
     * Hides the cursor
     */
    object HideCursor : Event()

    /**
     * A [org.codetome.zircon.api.screen.Screen] has been switched to
     * (eg: the `display` function has been called on a Screen object).
     */
    data class ScreenSwitch(val screenId: Identifier) : Event()

    /**
     * A component changed on a screen.
     */
    object ComponentChange : Event()

    /**
     * A component was added
     */
    object ComponentAddition : Event()

    /**
     * A component was removed
     */
    object ComponentRemoval : Event()

    fun fetchEventType(): String {
        return "EventType.${this::class.simpleName}"
    }
}
