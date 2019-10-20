package org.hexworks.zircon.internal.uievent.impl

import org.hexworks.cobalt.events.api.CancelState
import org.hexworks.cobalt.events.api.NotCancelled
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.uievent.ComponentEvent
import org.hexworks.zircon.api.uievent.ComponentEventSource
import org.hexworks.zircon.api.uievent.ComponentEventType
import org.hexworks.zircon.api.uievent.KeyboardEvent
import org.hexworks.zircon.api.uievent.KeyboardEventType
import org.hexworks.zircon.api.uievent.MouseEvent
import org.hexworks.zircon.api.uievent.MouseEventType
import org.hexworks.zircon.api.uievent.Pass
import org.hexworks.zircon.api.uievent.PreventDefault
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.api.uievent.StopPropagation
import org.hexworks.zircon.api.uievent.UIEvent
import org.hexworks.zircon.api.uievent.UIEventPhase
import org.hexworks.zircon.api.uievent.UIEventResponse
import org.hexworks.zircon.api.uievent.UIEventSource
import org.hexworks.zircon.api.uievent.UIEventType
import org.hexworks.zircon.internal.uievent.UIEventProcessor
import org.hexworks.zircon.internal.util.PersistentList

class DefaultUIEventProcessor : UIEventProcessor, UIEventSource, ComponentEventSource {

    private val logger = LoggerFactory.getLogger(this::class)
    private var listeners = PersistentMapFactory.create<UIEventType, PersistentList<InputEventSubscription>>()
    private var closed = false

    override fun close() {
        closed = true
        listeners.flatMap { it.value }.forEach {
            it.cancel()
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
        val subscriptions = listeners.getOrElse(eventType) { PersistentListFactory.create() }
        listeners = listeners.put(eventType, subscriptions.add(subscription))
        return subscription
    }

    private fun checkClosed() {
        if (closed) throw IllegalStateException("This UIEventProcessor is closed.")
    }

    inner class InputEventSubscription(
            private val eventType: UIEventType,
            val listener: (UIEvent, UIEventPhase) -> UIEventResponse) : Subscription {

        override var cancelState: CancelState = NotCancelled

        override fun cancel(cancelState: CancelState) {
            val subscription = this
            listeners[eventType]?.let { subscriptions ->
                listeners = listeners.put(eventType, subscriptions.remove(subscription))
            }
            this.cancelState = cancelState
        }
    }
}
