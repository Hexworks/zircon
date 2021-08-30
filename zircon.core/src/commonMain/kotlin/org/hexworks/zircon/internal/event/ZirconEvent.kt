package org.hexworks.zircon.internal.event

import org.hexworks.cobalt.core.api.UUID
import org.hexworks.cobalt.events.api.Event
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.component.InternalContainer

/**
 * Contains all the possible events that Zircon uses internally.
 */
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
     * @see [org.hexworks.zircon.internal.behavior.ComponentFocusOrderList]
     */
    data class RequestFocusFor(
        val component: Component,
        override val emitter: Any
    ) : ZirconEvent()

    /**
     * Requests to clear focus for the given [Component].
     * @see [org.hexworks.zircon.internal.behavior.ComponentFocusOrderList]
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
    data class ScreenSwitch(
        val screenId: UUID,
        override val emitter: Any
    ) : ZirconEvent()

    /**
     * A [component] was added to a container.
     */
    data class ComponentAdded(
        val parent: InternalContainer,
        val component: InternalComponent,
        override val emitter: Any
    ) : ZirconEvent()

    /**
     * A [component] was removed from a container.
     */
    data class ComponentRemoved(
        val parent: InternalContainer,
        val component: InternalComponent,
        override val emitter: Any
    ) : ZirconEvent()

}
