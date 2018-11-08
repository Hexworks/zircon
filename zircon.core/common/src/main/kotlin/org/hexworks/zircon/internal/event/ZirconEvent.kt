package org.hexworks.zircon.internal.event

import org.hexworks.cobalt.datatypes.Identifier
import org.hexworks.cobalt.events.api.Event
import org.hexworks.zircon.api.data.Position

sealed class ZirconEvent : Event {
    /**
     * A new input arrived into the system.
     */
    data class Input(val input: org.hexworks.zircon.api.input.Input) : ZirconEvent()

    /**
     * Cursor is requested at the given `position`.
     */
    data class RequestCursorAt(val position: Position) : ZirconEvent()

    /**
     * Hides the cursor
     */
    object HideCursor : ZirconEvent()

    /**
     * A [org.hexworks.zircon.api.screen.Screen] has been switched to
     * (eg: the `display` function has been called on a Screen object).
     */
    data class ScreenSwitch(val screenId: Identifier) : ZirconEvent()

    /**
     * A component was added
     */
    object ComponentAddition : ZirconEvent()

    /**
     * A component was removed
     */
    object ComponentRemoval : ZirconEvent()

    /**
     * A Hyperlink was triggered
     * *eg. used in [org.hexworks.zircon.api.component.LogArea]
     * */
    data class TriggeredHyperLink(val linkId: String) : ZirconEvent()
}
