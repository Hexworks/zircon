package org.hexworks.zircon.api.event

import org.hexworks.zircon.internal.util.ThreadSafeQueue
import org.hexworks.zircon.platform.factory.ThreadSafeMapFactory
import org.hexworks.zircon.platform.factory.ThreadSafeQueueFactory

@Suppress("UNCHECKED_CAST")
object EventBus {

    val subscriptions = ThreadSafeMapFactory.create<String, ThreadSafeQueue<EventBusSubscription<*>>>()

    /**
     * Subscribes to all events of the given event type.
     */
    inline fun <reified T : Event> subscribe(noinline callback: (T) -> Unit): EventBusSubscription<T> {
        val eventType = T::class.simpleName!!
        val subscription = EventBusSubscription(
                callback = callback,
                eventType = eventType)
        subscriptions.getOrPut(eventType) { ThreadSafeQueueFactory.create() }.add(subscription)
        return subscription
    }

    /**
     * Broadcasts an event to all listeners of this event type.
     */
    fun broadcast(event: Event) {
        subscriptions.getOrPut(event::class.simpleName!!) { ThreadSafeQueueFactory.create() }.forEach {
            (it.callback as (Event) -> Unit).invoke(event)
        }
    }

    fun unsubscribe(subscription: EventBusSubscription<*>) {
        subscriptions[subscription.eventType]?.remove(subscription)
    }

}
