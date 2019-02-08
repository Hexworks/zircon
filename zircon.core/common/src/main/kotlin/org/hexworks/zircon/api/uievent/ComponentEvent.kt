package org.hexworks.zircon.api.uievent

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.screen.Screen


/**
 * A [ComponentEvent] is a component-specific event which only happens in the context
 * of [Component]s (not for plain [TileGrid]s and [Screen]s. A [ComponentEvent] can
 * be originated from other [UIEvent]s like [KeyboardEvent] and [MouseEvent] or it
 * can also come from programmatic sources (like [Component.requestFocus]).
 *
 * [ComponentEvent]s don't have [UIEventPhase]s, but [ComponentEventHandler]s can
 * influence the [ComponentEvent] processing by returning [PreventDefault] or
 * [StopPropagation].
 */
data class ComponentEvent(
        /**
         * The type of the [ComponentEvent].
         */
        override val type: ComponentEventType) : UIEvent
