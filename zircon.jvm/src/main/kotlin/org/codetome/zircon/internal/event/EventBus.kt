package org.codetome.zircon.internal.event

import org.codetome.zircon.internal.util.Identifier
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@Suppress("UNCHECKED_CAST")
object EventBus {

    val subscriptions = ConcurrentHashMap<String, MutableList<Subscription<*>>>()

    /**
     * Subscribes to all events of the given event type.
     */
    inline fun <reified T : Event> subscribe(noinline callback: (T) -> Unit): Subscription<T> {
        val eventType = T::class.simpleName!!
        val subscription = Subscription(
                callback = callback,
                eventType = eventType)
        subscriptions.getOrPut(eventType, { mutableListOf() })?.add(subscription)
        return subscription
    }

    /**
     * Subscribes to all events of the given event type
     * which have the given `identifier`.
     */
    inline fun <reified T : Event> listenTo(identifier: Identifier, noinline callback: (T) -> Unit): Subscription<T> {
        val eventType = T::class.simpleName!!
        val subscription = Subscription(
                callback = callback,
                eventType = eventType,
                identifier = Optional.of(identifier))
        subscriptions.getOrPut(eventType, { mutableListOf() })?.add(subscription)
        return subscription
    }

    /**
     * Sends the given `event` to the subscriber having the given [Identifier].
     */
    fun sendTo(identifier: Identifier, event: Event) {
        subscriptions.getOrDefault(event.fetchEventType(), mutableListOf())
                .filter { it.hasIdentifier(identifier) }
                .forEach { (it.callback as (Event) -> Unit).invoke(event) }
    }

    /**
     * Broadcasts an event to all listeners of this event type.
     */
    fun broadcast(event: Event) {
        subscriptions.getOrDefault(event.fetchEventType(), mutableListOf()).forEach {
            (it.callback as (Event) -> Unit).invoke(event)
        }
    }

    fun unsubscribe(subscription: Subscription<*>) {
        subscriptions[subscription.eventType]?.remove(subscription)
    }

}
