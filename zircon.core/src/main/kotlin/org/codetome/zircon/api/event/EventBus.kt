package org.codetome.zircon.api.event

import org.codetome.zircon.api.util.Identifier
import org.codetome.zircon.api.util.Maybe
import org.codetome.zircon.internal.util.ThreadSafeQueue
import org.codetome.zircon.platform.factory.ThreadSafeMapFactory
import org.codetome.zircon.platform.factory.ThreadSafeQueueFactory

@Suppress("UNCHECKED_CAST")
object EventBus {

    val subscriptions = ThreadSafeMapFactory.create<String, ThreadSafeQueue<Subscription<*>>>()

    /**
     * Subscribes to all events of the given event type.
     */
    inline fun <reified T : Event> subscribe(noinline callback: (T) -> Unit): Subscription<T> {
        val eventType = T::class.simpleName!!
        val subscription = Subscription(
                callback = callback,
                eventType = eventType)
        subscriptions.getOrPut(eventType) { ThreadSafeQueueFactory.create() }.add(subscription)
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
                identifier = Maybe.of(identifier))
        subscriptions.getOrPut(eventType) { ThreadSafeQueueFactory.create() }.add(subscription)
        return subscription
    }

    /**
     * Sends the given `event` to the subscriber having the given [Identifier].
     */
    fun sendTo(identifier: Identifier, event: Event) {
        subscriptions.getOrPut(event::class.simpleName!!) { ThreadSafeQueueFactory.create() }
                .filter { it.hasIdentifier(identifier) }
                .forEach { (it.callback as (Event) -> Unit).invoke(event) }
    }

    /**
     * Broadcasts an event to all listeners of this event type.
     */
    fun broadcast(event: Event) {
        subscriptions.getOrPut(event::class.simpleName!!) { ThreadSafeQueueFactory.create() }.forEach {
            (it.callback as (Event) -> Unit).invoke(event)
        }
    }

    fun unsubscribe(subscription: Subscription<*>) {
        subscriptions[subscription.eventType]?.remove(subscription)
    }

}
