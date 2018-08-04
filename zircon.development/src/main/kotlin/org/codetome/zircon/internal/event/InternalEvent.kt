package org.codetome.zircon.internal.event

import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.event.Event
import org.codetome.zircon.api.input.KeyStroke
import org.codetome.zircon.api.input.MouseAction
import org.codetome.zircon.api.util.Identifier

sealed class InternalEvent : Event {
    /**
     * A new input arrived into the system.
     */
    data class Input(val input: org.codetome.zircon.api.input.Input) : InternalEvent()

    /**
     * A key was pressed.
     */
    data class KeyPressed(val keyStroke: KeyStroke) : InternalEvent()

    /**
     * A mouse button was pressed.
     */
    data class MousePressed(val mouseAction: MouseAction) : InternalEvent()

    /**
     * A mouse button was released.
     */
    data class MouseReleased(val mouseAction: MouseAction) : InternalEvent()

    /**
     * The mouse moved.
     */
    data class MouseMoved(val mouseAction: MouseAction) : InternalEvent()
    /**
     * A component was hovered over.
     */
    data class MouseOver(val mouseAction: MouseAction) : InternalEvent()

    /**
     * A component is no longer hovered.
     */
    data class MouseOut(val mouseAction: MouseAction) : InternalEvent()

    /**
     * Cursor is requested at the given `position`.
     */
    data class RequestCursorAt(val position: Position) : InternalEvent()

    /**
     * Hides the cursor
     */
    object HideCursor : InternalEvent()

    /**
     * A [org.codetome.zircon.api.screen.Screen] has been switched to
     * (eg: the `display` function has been called on a Screen object).
     */
    data class ScreenSwitch(val screenId: Identifier) : InternalEvent()

    /**
     * A component was added
     */
    object ComponentAddition : InternalEvent()

    /**
     * A component was removed
     */
    object ComponentRemoval : InternalEvent()
}
