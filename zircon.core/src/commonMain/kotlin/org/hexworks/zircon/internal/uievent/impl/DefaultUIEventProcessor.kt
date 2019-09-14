package org.hexworks.zircon.internal.uievent.impl

import org.hexworks.cobalt.events.api.CancelState
import org.hexworks.cobalt.events.api.NotCancelled
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.uievent.ComponentEvent
import org.hexworks.zircon.api.uievent.ComponentEventHandler
import org.hexworks.zircon.api.uievent.ComponentEventProcessor
import org.hexworks.zircon.api.uievent.ComponentEventSource
import org.hexworks.zircon.api.uievent.ComponentEventType
import org.hexworks.zircon.api.uievent.KeyboardEvent
import org.hexworks.zircon.api.uievent.KeyboardEventHandler
import org.hexworks.zircon.api.uievent.KeyboardEventProcessor
import org.hexworks.zircon.api.uievent.KeyboardEventType
import org.hexworks.zircon.api.uievent.MouseEvent
import org.hexworks.zircon.api.uievent.MouseEventHandler
import org.hexworks.zircon.api.uievent.MouseEventProcessor
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
import org.hexworks.zircon.internal.util.ThreadSafeQueue
import org.hexworks.zircon.platform.factory.ThreadSafeMapFactory
import org.hexworks.zircon.platform.factory.ThreadSafeQueueFactory

class DefaultUIEventProcessor : UIEventProcessor, UIEventSource, ComponentEventSource {

    private val logger = LoggerFactory.getLogger(this::class)
    private val listeners = ThreadSafeMapFactory.create<UIEventType, ThreadSafeQueue<InputEventSubscription>>()
    private var closed = false

    override fun close() {
        closed = true
        listeners.flatMap { it.value }.forEach {
            it.cancel()
        }
        listeners.clear()
    }

    override fun process(event: UIEvent, phase: UIEventPhase): UIEventResponse {
        checkClosed()
        return listeners[event.type]?.let { list ->
            var finalResult: UIEventResponse = Pass
            list.forEach {
                val result = it.listener.invoke(event, phase)
                when (result) {
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

    override fun handleMouseEvents(eventType: MouseEventType, handler: MouseEventHandler): Subscription {
        checkClosed()
        return buildSubscription(eventType) { event, phase ->
            handler.handle(event as MouseEvent, phase)
        }
    }

    override fun processMouseEvents(eventType: MouseEventType, handler: MouseEventProcessor): Subscription {
        checkClosed()
        return buildSubscription(eventType) { event, phase ->
            handler.process(event as MouseEvent, phase)
            Processed
        }
    }

    override fun handleKeyboardEvents(eventType: KeyboardEventType, handler: KeyboardEventHandler): Subscription {
        checkClosed()
        return buildSubscription(eventType) { event, phase ->
            handler.handle(event as KeyboardEvent, phase)
        }
    }

    override fun processKeyboardEvents(eventType: KeyboardEventType, handler: KeyboardEventProcessor): Subscription {
        checkClosed()
        return buildSubscription(eventType) { event, phase ->
            handler.process(event as KeyboardEvent, phase)
            Processed
        }
    }

    override fun handleComponentEvents(eventType: ComponentEventType, handler: ComponentEventHandler): Subscription {
        checkClosed()
        return buildSubscription(eventType) { event, phase ->
            if (phase == UIEventPhase.TARGET) {
                handler.handle(event as ComponentEvent)
            } else Pass
        }
    }

    override fun processComponentEvents(eventType: ComponentEventType, handler: ComponentEventProcessor): Subscription {
        checkClosed()
        return buildSubscription(eventType) { event, phase ->
            if (phase == UIEventPhase.TARGET) {
                handler.process(event as ComponentEvent)
            }
            Processed
        }
    }

    private fun buildSubscription(eventType: UIEventType, listener: (UIEvent, UIEventPhase) -> UIEventResponse): Subscription {
        return listeners.getOrPut(eventType) { ThreadSafeQueueFactory.create() }.let {
            val subscription = InputEventSubscription(
                    listener = listener,
                    subscriptions = it)
            it.add(subscription)
            subscription
        }
    }

    private fun checkClosed() {
        if (closed) throw IllegalStateException("This UIEventProcessor is closed.")
    }

    class InputEventSubscription(
            val listener: (UIEvent, UIEventPhase) -> UIEventResponse,
            private val subscriptions: ThreadSafeQueue<InputEventSubscription>) : Subscription {

        override var cancelState: CancelState = NotCancelled

        override fun cancel(cancelState: CancelState) {
            subscriptions.remove(this)
            this.cancelState = cancelState
        }
    }
}
