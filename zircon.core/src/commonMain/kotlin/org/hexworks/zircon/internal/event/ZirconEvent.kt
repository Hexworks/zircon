package org.hexworks.zircon.internal.event

import kotlinx.collections.immutable.PersistentList
import org.hexworks.cobalt.core.api.UUID
import org.hexworks.cobalt.events.api.Event
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.component.InternalContainer
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
    data class ScreenSwitch(val screenId: UUID,
                            override val emitter: Any) : ZirconEvent()

    /**
     * An attached component was moved within a container.
     */
    data class ComponentMoved(override val emitter: Any) : ZirconEvent()

    /**
     * A [component] was added to a [parent] container.
     */
    data class ComponentAdded(
            val parent: InternalContainer,
            val component: InternalComponent,
            override val emitter: Any
    ) : ZirconEvent()

    /**
     * A [component] was removed to a [parent] container.
     * This always happens after [ComponentDetached].
     */
    data class ComponentRemoved(
            val parent: InternalContainer,
            val component: InternalComponent,
            override val emitter: Any
    ) : ZirconEvent()

    /**
     * A [component] was detached to a [parent] container.
     * This always happens before [ComponentRemoved].
     */
    data class ComponentDetached(
            val parent: Component,
            val component: Component,
            override val emitter: Any
    ) : ZirconEvent()

}
