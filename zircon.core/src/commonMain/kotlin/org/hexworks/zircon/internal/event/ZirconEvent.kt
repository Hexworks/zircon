package org.hexworks.zircon.internal.event

import org.hexworks.cobalt.core.api.Identifier
import org.hexworks.cobalt.events.api.Event
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.internal.data.LayerState

sealed class ZirconEvent : Event {

    /**
     * Cursor is requested at the given `position`.
     */
    data class RequestCursorAt(
            val position: Position,
            override val emitter: Any
    ) : ZirconEvent()

    /**
     * Requests focus for the given [Component].
     * @see [org.hexworks.zircon.internal.behavior.ComponentFocusHandler]
     */
    data class RequestFocusFor(
            val component: Component,
            override val emitter: Any
    ) : ZirconEvent()

    /**
     * Requests to clear focus for the given [Component].
     * @see [org.hexworks.zircon.internal.behavior.ComponentFocusHandler]
     */
    data class ClearFocus(
            val component: Component,
            override val emitter: Any
    ) : ZirconEvent()

    /**
     * Hides the cursor
     */
    data class HideCursor(override val emitter: Any) : ZirconEvent()

    /**
     * A [org.hexworks.zircon.api.screen.Screen] has been switched to
     * (eg: the `display` function has been called on a Screen data class).
     */
    data class ScreenSwitch(val screenId: Identifier,
                            override val emitter: Any) : ZirconEvent()

    /**
     * An attached component was moved within a container.
     */
    data class ComponentMoved(override val emitter: Any) : ZirconEvent()

    /**
     * A component was added to a component container.
     */
    data class ComponentAdded(override val emitter: Any) : ZirconEvent()

    /**
     * A component was removed
     */
    data class ComponentRemoved(override val emitter: Any) : ZirconEvent()

    /**
     * A layer was added.
     */
    data class LayerAdded(override val emitter: Any) : ZirconEvent()

    /**
     * A layer was removed.
     */
    data class LayerRemoved(override val emitter: Any) : ZirconEvent()

    /**
     * This event is fired when the state of a layer changes.
     */
    data class LayerChanged(
            val state: LayerState,
            override val emitter: Any
    ) : ZirconEvent()
}
