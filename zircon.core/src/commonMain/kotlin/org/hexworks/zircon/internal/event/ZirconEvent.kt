package org.hexworks.zircon.internal.event

import org.hexworks.cobalt.core.api.UUID
import org.hexworks.cobalt.events.api.Event
import org.hexworks.cobalt.events.api.EventDescriptor
import org.hexworks.cobalt.events.api.EventSource
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
        override val emitter: EventSource,
    ) : ZirconEvent() {
        override val key = Companion.key

        companion object : EventDescriptor<RequestCursorAt> {
            override val key = "RequestCursorAt"
            override val eventType = RequestCursorAt::class
        }
    }


    /**
     * Requests focus for the given [Component].
     * @see [org.hexworks.zircon.internal.behavior.ComponentFocusOrderList]
     */
    data class RequestFocusFor(
        val component: Component,
        override val emitter: EventSource
    ) : ZirconEvent() {
        override val key = Companion.key

        companion object : EventDescriptor<RequestFocusFor> {
            override val key = "RequestFocusFor"
            override val eventType = RequestFocusFor::class
        }
    }

    /**
     * Requests to clear focus for the given [Component].
     * @see [org.hexworks.zircon.internal.behavior.ComponentFocusOrderList]
     */
    data class ClearFocus(
        val component: Component,
        override val emitter: EventSource
    ) : ZirconEvent() {
        override val key = Companion.key

        companion object : EventDescriptor<ClearFocus> {
            override val key = "ClearFocus"
            override val eventType = ClearFocus::class
        }
    }

    /**
     * Hides the cursor
     */
    data class HideCursor(override val emitter: EventSource) : ZirconEvent() {
        override val key = Companion.key

        companion object : EventDescriptor<HideCursor> {
            override val key = "HideCursor"
            override val eventType = HideCursor::class
        }
    }

    /**
     * A [org.hexworks.zircon.api.screen.Screen] has been switched to
     * (eg: the `display` function has been called on a Screen data class).
     */
    data class ScreenSwitch(
        val screenId: UUID,
        override val emitter: EventSource
    ) : ZirconEvent() {
        override val key = Companion.key

        companion object : EventDescriptor<ScreenSwitch> {
            override val key = "ScreenSwitch"
            override val eventType = ScreenSwitch::class
        }
    }

    /**
     * A [component] was added to a container.
     */
    data class ComponentAdded(
        val parent: InternalContainer,
        val component: InternalComponent,
        override val emitter: EventSource
    ) : ZirconEvent() {
        override val key = Companion.key

        companion object : EventDescriptor<ComponentAdded> {
            override val key = "ComponentAdded"
            override val eventType = ComponentAdded::class
        }
    }

    /**
     * A [component] was removed from a container.
     */
    data class ComponentRemoved(
        val parent: InternalContainer,
        val component: InternalComponent,
        override val emitter: EventSource
    ) : ZirconEvent() {
        override val key = Companion.key

        companion object : EventDescriptor<ComponentRemoved> {
            override val key = "ComponentRemoved"
            override val eventType = ComponentRemoved::class
        }
    }

}
