package test

import org.hexworks.cobalt.events.api.CancelState
import org.hexworks.cobalt.events.api.NotCancelled
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.internal.util.ThreadSafeQueue
import org.hexworks.zircon.platform.factory.ThreadSafeMapFactory
import org.hexworks.zircon.platform.factory.ThreadSafeQueueFactory

// Problems we're trying to solve:
//
// - Input is a sealed class which is hard to extend. Can't add new
//   types to it and can't change old ones
// - Memory leak is possible because all event listeners are tied to
//   the EventBus
// - No return value for event listeners, so bubbling / capture is not possible
// - The Inputs themselves are hard to use and pattern match

val TARGET = Component("Target")
val PARENT = Component("Parent")
val ROOT = Component("Root")

object NewInputHandlingDemo {

    @JvmStatic
    fun main(args: Array<String>) {

        val dispatcher = EventDispatcher()

        PARENT.onMouseEvent(MouseEventType.MOUSE_CLICKED) { evt, _ ->
            evt == MouseEvent(
                    type = MouseEventType.MOUSE_CLICKED,
                    button = 1,
                    position = Positions.create(2, 3))
        }

        dispatcher.dispatch(MouseEvent(
                type = MouseEventType.MOUSE_CLICKED,
                button = 1,
                position = Positions.create(2, 3)))
    }
}

/**
 * Interface for representing event types.
 */
interface EventType

enum class EventPhase {
    CAPTURE,
    TARGET,
    BUBBLE
}


/**
 * By having UIEvent as a interface we retain the ability to add
 * custom events
 * We can still use specific implementations like [MouseEventType] and [KeyboardEventType]
 */
interface UIEvent {
    val type: EventType
}

enum class MouseEventType : EventType {
    MOUSE_CLICKED,
    MOUSE_PRESSED,
    MOUSE_RELEASED,
    MOUSE_ENTERED,
    MOUSE_EXITED,
    MOUSE_WHEEL_ROTATED_UP,
    MOUSE_WHEEL_ROTATED_DOWN,
    MOUSE_DRAGGED,
    MOUSE_MOVED
}

enum class KeyboardEventType : EventType {
    KEY_PRESSED,
    KEY_TYPED,
    KEY_RELEASED
}

enum class KeyCode {
    Key_a, Key_b, Key_A, Key_1, Enter, Tab // ...
}

data class MouseEvent(override val type: MouseEventType,
                      val button: Int,
                      val position: Position) : UIEvent

data class KeyboardEvent(override val type: KeyboardEventType,
                         val key: String,
                         val code: KeyCode,
                         val ctrlDown: Boolean = false,
                         val altDown: Boolean = false,
                         val metaDown: Boolean = false,
                         val shiftDown: Boolean = false) : UIEvent

/**
 * Implements event propagation (capture, target, bubble). This is what
 * we call `ComponentContainer` today.
 */
class EventDispatcher {


    /**
     * Internal API. This will return `false` if there are no listeners
     * for [Event.eventType] or if invoking all listeners returned `false`.
     * Note that this will short-circuit when any of the listeners returns `true`.
     */
    internal fun dispatch(event: UIEvent): Boolean {

        // we'll fetch this by other means in a real implementation
        val target = TARGET
        val path = target.pathFromRoot()

        for (component in path) {
            if (component.process(event, EventPhase.CAPTURE)) {
                println("Component ${component.name} captured event $event")
                return true
            }
        }

        if (target.process(event, EventPhase.TARGET)) {
            println("Target component ${target.name} handled event $event")
            return true
        }

        for (component in path.reversed()) {
            if (component.process(event, EventPhase.BUBBLE)) {
                println("Component ${target.name} stopped bubbling of event $event")
                return true
            }
        }
        return false
    }

}

// will be closeable
class Component(val name: String,
                private val defaultEventProcessor: DefaultEventProcessor = DefaultEventProcessor())
    : EventProcessor by defaultEventProcessor,
        UIEventEmitter by defaultEventProcessor,
        KeyboardEventListener, MouseEventListener {

    init {
        defaultEventProcessor.onMouseEvent(MouseEventType.MOUSE_PRESSED, this::mousePressed)
        defaultEventProcessor.onMouseEvent(MouseEventType.MOUSE_RELEASED, this::mouseReleased)
        defaultEventProcessor.onKeyboardEvent(KeyboardEventType.KEY_PRESSED, this::keyPressed)
    }

    // in the real implementation this will just traverse the `parent` pointers
    fun pathFromRoot(): List<Component> {
        return listOf(ROOT, PARENT, TARGET)
    }
}

interface EventProcessor {
    fun process(event: UIEvent, phase: EventPhase): Boolean
}

interface UIEventEmitter {

    fun onMouseEvent(eventType: MouseEventType, fn: (MouseEvent, EventPhase) -> Boolean): Subscription

    fun onKeyboardEvent(eventType: KeyboardEventType, fn: (KeyboardEvent, EventPhase) -> Boolean): Subscription
}

/**
 * Processes [Event]s.
 */
class DefaultEventProcessor : EventProcessor, UIEventEmitter {

    private val listeners = ThreadSafeMapFactory.create<EventType, ThreadSafeQueue<InputEventSubscription>>()

    /**
     * Internal API. This will return `false` if there are no listeners
     * for [Event.eventType] or if invoking all listeners returned `false`.
     * Note that this will short-circuit when any of the listeners returns `true`.
     */
    override fun process(event: UIEvent, phase: EventPhase): Boolean {
        return listeners[event.type]?.let { list ->
            list.any { it.listener.invoke(event, phase) }
        } ?: false
    }

    // TODO: we can add an `onEvent` later?

    // By using this setup we don't have a proliferation of `on*` functions but we still
    // retain all functionality.
    /**
     * External API.
     */
    @Suppress("UNCHECKED_CAST")
    override fun onMouseEvent(eventType: MouseEventType, fn: (MouseEvent, EventPhase) -> Boolean): Subscription {
        return buildSubscription(eventType, fn as (UIEvent, EventPhase) -> Boolean)
    }

    /**
     * External API.
     */
    @Suppress("UNCHECKED_CAST")
    override fun onKeyboardEvent(eventType: KeyboardEventType, fn: (KeyboardEvent, EventPhase) -> Boolean): Subscription {
        return buildSubscription(eventType, fn as (UIEvent, EventPhase) -> Boolean)
    }

    private fun buildSubscription(eventType: EventType, listener: (UIEvent, EventPhase) -> Boolean): Subscription {
        return listeners.getOrPut(eventType) { ThreadSafeQueueFactory.create() }.let {
            val subscription = InputEventSubscription(
                    listener = listener,
                    subscriptions = it)
            it.add(subscription)
            subscription
        }
    }
}


interface MouseEventListener {

    fun mousePressed(event: MouseEvent, phase: EventPhase): Boolean = false

    fun mouseReleased(event: MouseEvent, phase: EventPhase): Boolean = false

    // ...

}

interface KeyboardEventListener {

    fun keyPressed(event: KeyboardEvent, phase: EventPhase): Boolean = false

    // ...

}

class InputEventSubscription(
        val listener: (UIEvent, EventPhase) -> Boolean,
        private val subscriptions: ThreadSafeQueue<InputEventSubscription>) : Subscription {

    override var cancelState: CancelState = NotCancelled

    override fun cancel(cancelState: CancelState) {
        subscriptions.remove(this)
        this.cancelState = cancelState
    }
}



