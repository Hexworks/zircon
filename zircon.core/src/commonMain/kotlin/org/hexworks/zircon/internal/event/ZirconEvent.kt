package org.hexworks.zircon.internal.event

import org.hexworks.cobalt.Identifier
import org.hexworks.cobalt.events.api.Event
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.internal.data.LayerState
import org.hexworks.zircon.api.data.Position

sealed class ZirconEvent : Event {

    /**
     * Cursor is requested at the given `position`.
     */
    data class RequestCursorAt(val position: Position) : ZirconEvent()

    /**
     * Requests focus for the given [Component].
     * @see [org.hexworks.zircon.internal.behavior.ComponentFocusHandler]
     */
    data class RequestFocusFor(val component: Component) : ZirconEvent()

    /**
     * Requests to clear focus for the given [Component].
     * @see [org.hexworks.zircon.internal.behavior.ComponentFocusHandler]
     */
    data class ClearFocus(val component: Component) : ZirconEvent()

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
     * An attached component was moved within a container.
     */
    object ComponentMoved : ZirconEvent()

    /**
     * A component was added to a component container.
     */
    object ComponentAdded : ZirconEvent()

    /**
     * A component was removed
     */
    object ComponentRemoved : ZirconEvent()

    /**
     * A layer was added.
     */
    object LayerAdded : ZirconEvent()

    /**
     * A layer was removed.
     */
    object LayerRemoved : ZirconEvent()

    /**
     * This event is fired when the state of a layer changes.
     */
    data class LayerChanged(val state: LayerState) : ZirconEvent()
}
