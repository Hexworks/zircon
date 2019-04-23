package org.hexworks.zircon.internal.uievent.impl

import org.hexworks.cobalt.events.api.CancelState
import org.hexworks.cobalt.events.api.NotCancelled
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.cobalt.logging.api.LoggerFactory
import org.hexworks.zircon.api.uievent.*
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

    // TODO: we can add an `onEvent` later?

    override fun onMouseEvent(eventType: MouseEventType, handler: MouseEventHandler): Subscription {
        checkClosed()
        return buildSubscription(eventType) { event, phase ->
            handler.handle(event as MouseEvent, phase)
        }
    }

    override fun onKeyboardEvent(eventType: KeyboardEventType, handler: KeyboardEventHandler): Subscription {
        checkClosed()
        return buildSubscription(eventType) { event, phase ->
            handler.handle(event as KeyboardEvent, phase)
        }
    }

    override fun onComponentEvent(eventType: ComponentEventType, handler: ComponentEventHandler): Subscription {
        checkClosed()
        return buildSubscription(eventType) { event, phase ->
            if (phase == UIEventPhase.TARGET) {
                handler.handle(event as ComponentEvent)
            } else Pass
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
