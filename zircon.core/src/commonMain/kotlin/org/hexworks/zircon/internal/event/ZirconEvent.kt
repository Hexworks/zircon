package org.hexworks.zircon.internal.event

import kotlin.reflect.KClass
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
public sealed class ZirconEvent : Event {

    /**
     * Cursor is requested at the given `position`.
     */
    public data class RequestCursorAt(
        val position: Position,
        override val emitter: EventSource,
    ) : ZirconEvent() {
        override val key: String = Companion.key

        public companion object : EventDescriptor<RequestCursorAt> {
            override val key: String = "RequestCursorAt"
            override val eventType: KClass<RequestCursorAt> = RequestCursorAt::class
        }
    }


    /**
     * Requests focus for the given [Component].
     * @see [org.hexworks.zircon.internal.behavior.ComponentFocusOrderList]
     */
    public data class RequestFocusFor(
        val component: Component,
        override val emitter: EventSource
    ) : ZirconEvent() {
        override val key: String = Companion.key

        public companion object : EventDescriptor<RequestFocusFor> {
            override val key: String = "RequestFocusFor"
            override val eventType: KClass<RequestFocusFor> = RequestFocusFor::class
        }
    }

    /**
     * Requests to clear focus for the given [Component].
     * @see [org.hexworks.zircon.internal.behavior.ComponentFocusOrderList]
     */
    public data class ClearFocus(
        val component: Component,
        override val emitter: EventSource
    ) : ZirconEvent() {
        override val key: String = Companion.key

        public companion object : EventDescriptor<ClearFocus> {
            override val key: String = "ClearFocus"
            override val eventType: KClass<ClearFocus> = ClearFocus::class
        }
    }

    /**
     * Hides the cursor
     */
    public data class HideCursor(override val emitter: EventSource) : ZirconEvent() {
        override val key: String = Companion.key

        public companion object : EventDescriptor<HideCursor> {
            override val key: String = "HideCursor"
            override val eventType: KClass<HideCursor> = HideCursor::class
        }
    }

    /**
     * A [org.hexworks.zircon.api.screen.Screen] has been switched to
     * (eg: the `display` function has been called on a Screen data class).
     */
    public data class ScreenSwitch(
        val screenId: UUID,
        override val emitter: EventSource
    ) : ZirconEvent() {
        override val key: String = Companion.key

        public companion object : EventDescriptor<ScreenSwitch> {
            override val key: String = "ScreenSwitch"
            override val eventType: KClass<ScreenSwitch> = ScreenSwitch::class
        }
    }

    /**
     * A [component] was added to a container.
     */
    public data class ComponentAdded(
        val parent: InternalContainer,
        val component: InternalComponent,
        override val emitter: EventSource
    ) : ZirconEvent() {
        override val key: String = Companion.key

        public companion object : EventDescriptor<ComponentAdded> {
            override val key: String = "ComponentAdded"
            override val eventType: KClass<ComponentAdded> = ComponentAdded::class
        }
    }

    /**
     * A [component] was removed from a container.
     */
    public data class ComponentRemoved(
        val parent: InternalContainer,
        val component: InternalComponent,
        override val emitter: EventSource
    ) : ZirconEvent() {
        override val key: String = Companion.key

        public companion object : EventDescriptor<ComponentRemoved> {
            override val key: String = "ComponentRemoved"
            override val eventType: KClass<ComponentRemoved> = ComponentRemoved::class
        }
    }

}
