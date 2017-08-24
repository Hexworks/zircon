package org.codetome.zircon.internal.event

import java.util.*
import java.util.concurrent.ConcurrentHashMap

@Suppress("UNCHECKED_CAST")
object EventBus {

    val subscriptions = ConcurrentHashMap<EventType, MutableList<Subscription<*>>>()

    fun subscribe(type: EventType, callback: () -> Unit): Subscription<Unit> {
        return subscribe<Unit>(type, {
            callback()
        })
    }

    inline fun <reified T : Any> subscribe(type: EventType,
                                           noinline callback: (Event<T>) -> Unit,
                                           keys: Set<String> = setOf()): Subscription<T> {
        val subscription = Subscription(
                keys = keys,
                callback = callback,
                dataType = T::class.java,
                eventType = type)
        log("Subscribing callback to event type: '$type'. Subscription: $subscription")
        subscriptions.getOrPut(type, { mutableListOf() })?.add(subscription)
        return subscription
    }

    /**
     * Emits an [Event] which contains no data.
     */
    fun emit(type: EventType) {
        emit(type, Unit)
    }

    inline fun <reified T : Any> emit(type: EventType, data: T, keys: Set<String> = setOf()) {
        log("+++ Emitting event of type '$type' with data: '$data'.")
        subscriptions[type]?.filter {
            it.dataType.isAssignableFrom(T::class.java)
        }?.filter {
            subscriberKeysIntersectsWithEventKeys(it, keys)
                    .or(subscriberHasNoKeys(it))
        }?.forEach {
            (it.callback as (Event<T>) -> Unit).invoke(Event(
                    type = type,
                    keys = keys,
                    data = data
            ))
        }
    }

    fun unsubscribe(subscription: Subscription<*>) {
        subscriptions[subscription.eventType]?.remove(subscription)
    }

    fun subscriberHasNoKeys(it: Subscription<*>) = it.keys.isEmpty()

    fun subscriberKeysIntersectsWithEventKeys(it: Subscription<*>, keys: Set<String>)
            = it.keys.minus(keys).size < it.keys.size

    fun log(text: String) {
//        println("${System.currentTimeMillis()}: $text")
    }
}