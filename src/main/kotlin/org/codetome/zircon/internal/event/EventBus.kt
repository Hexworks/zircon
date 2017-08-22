package org.codetome.zircon.internal.event

import java.util.concurrent.ConcurrentHashMap

@Suppress("UNCHECKED_CAST")
object EventBus {

    @PublishedApi
    internal class Subscription<T : Any>(val keys: Set<String>,
                                         val callback: (Event<T>) -> Unit,
                                         val dataType: Class<T>)

    @PublishedApi
    internal val subscriptions = ConcurrentHashMap<EventType, MutableList<Subscription<*>>>()

    fun subscribe(type: EventType, callback: () -> Unit) {
        subscribe<Unit>(type, {
            callback()
        })
    }

    @JvmStatic
    inline fun <reified T : Any> subscribe(type: EventType,
                                           noinline callback: (Event<T>) -> Unit,
                                           keys: Set<String> = setOf()) {
        subscriptions.getOrPut(type, { mutableListOf() })?.add(Subscription(
                keys = keys,
                callback = callback,
                dataType = T::class.java))
    }

    /**
     * Emits an [Event] which contains no data.
     */
    fun emit(type: EventType) {
        emit(type, Unit)
    }

    @JvmStatic
    inline fun <reified T : Any> emit(type: EventType, data: T, keys: Set<String> = setOf()) {
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

    @PublishedApi
    internal fun subscriberHasNoKeys(it: Subscription<*>) = it.keys.isEmpty()

    @PublishedApi
    internal fun subscriberKeysIntersectsWithEventKeys(it: Subscription<*>, keys: Set<String>)
            = it.keys.minus(keys).size < it.keys.size
}