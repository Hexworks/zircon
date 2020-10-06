package org.hexworks.zircon.internal.uievent.impl

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentHashMapOf
import org.hexworks.cobalt.core.behavior.DisposeState
import org.hexworks.cobalt.core.behavior.NotDisposed
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.uievent.*
import org.hexworks.zircon.internal.uievent.UIEventProcessor

class DefaultUIEventProcessor : UIEventProcessor, UIEventSource, ComponentEventSource {

    private val logger = LoggerFactory.getLogger(this::class)
    private var listeners = persistentHashMapOf<UIEventType, PersistentList<InputEventSubscription>>()
    override val isClosed = false.toProperty()

    override fun close() {
        isClosed.value = true
        listeners.flatMap { it.value }.forEach {
            it.dispose()
        }
        listeners = listeners.clear()
    }

    override fun process(event: UIEvent, phase: UIEventPhase): UIEventResponse {
        checkClosed()
        return listeners[event.type]?.let { list ->
            var finalResult: UIEventResponse = Pass
            list.forEach {
                when (val result = it.listener.invoke(event, phase)) {
                    Processed,
                    PreventDefault -> if (result.hasPrecedenceOver(finalResult)) {
                        finalResult = result
                    }
                    StopPropagation -> {
                        finalResult = result
                        return@forEach
                    }
                    Pass -> {
                        logger.debug("Result of invoking listener was 'Pass'. Result is ignored.")
                    }
                }
            }
            finalResult
        } ?: Pass
    }

    override fun handleMouseEvents(
            eventType: MouseEventType,
            handler: (event: MouseEvent, phase: UIEventPhase) -> UIEventResponse): Subscription {
        checkClosed()
        return buildSubscription(eventType) { event, phase ->
            handler(event as MouseEvent, phase)
        }
    }

    override fun processMouseEvents(
            eventType: MouseEventType,
            handler: (event: MouseEvent, phase: UIEventPhase) -> Unit): Subscription {
        checkClosed()
        return buildSubscription(eventType) { event, phase ->
            handler(event as MouseEvent, phase)
            Processed
        }
    }

    override fun handleKeyboardEvents(
            eventType: KeyboardEventType,
            handler: (event: KeyboardEvent, phase: UIEventPhase) -> UIEventResponse): Subscription {
        checkClosed()
        return buildSubscription(eventType) { event, phase ->
            handler(event as KeyboardEvent, phase)
        }
    }

    override fun processKeyboardEvents(
            eventType: KeyboardEventType,
            handler: (event: KeyboardEvent, phase: UIEventPhase) -> Unit): Subscription {
        checkClosed()
        return buildSubscription(eventType) { event, phase ->
            handler(event as KeyboardEvent, phase)
            Processed
        }
    }

    override fun handleComponentEvents(
            eventType: ComponentEventType,
            handler: (event: ComponentEvent) -> UIEventResponse): Subscription {
        checkClosed()
        return buildSubscription(eventType) { event, phase ->
            if (phase == UIEventPhase.TARGET) {
                handler(event as ComponentEvent)
            } else Pass
        }
    }

    override fun processComponentEvents(
            eventType: ComponentEventType,
            handler: (event: ComponentEvent) -> Unit): Subscription {
        checkClosed()
        return buildSubscription(eventType) { event, phase ->
            if (phase == UIEventPhase.TARGET) {
                handler(event as ComponentEvent)
            }
            Processed
        }
    }

    private fun buildSubscription(eventType: UIEventType, listener: (UIEvent, UIEventPhase) -> UIEventResponse): Subscription {
        val subscription = InputEventSubscription(
                eventType = eventType,
                listener = listener)
        val subscriptions = listeners.getOrElse(eventType) { persistentListOf() }
        listeners = listeners.put(eventType, subscriptions.add(subscription))
        return subscription
    }

    private fun checkClosed() {
        if (isClosed.value) throw IllegalStateException("This UIEventProcessor is closed.")
    }

    inner class InputEventSubscription(
            private val eventType: UIEventType,
            val listener: (UIEvent, UIEventPhase) -> UIEventResponse) : Subscription {

        override var disposeState: DisposeState = NotDisposed

        override fun dispose(disposeState: DisposeState) {
            val subscription = this
            listeners[eventType]?.let { subscriptions ->
                listeners = listeners.put(eventType, subscriptions.remove(subscription))
            }
            this.disposeState = disposeState
        }
    }
}
