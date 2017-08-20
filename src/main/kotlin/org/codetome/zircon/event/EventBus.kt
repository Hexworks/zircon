package org.codetome.zircon.event

import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
internal object EventBus {

    @PublishedApi
    internal class Subscription<T : Any>(val keys: Set<String>,
                                         val callback: (Event<T>) -> Unit,
                                         val dataType: KClass<T>)

    @PublishedApi
    internal val subscriptions: Map<EventType, MutableList<Subscription<*>>>
            = EventType.values()
            .map { Pair(it, mutableListOf<Subscription<*>>()) }
            .toMap()

    inline fun <reified T : Any> subscribe(type: EventType,
                                           noinline callback: (Event<T>) -> Unit,
                                           keys: Set<String> = setOf()) {
        subscriptions[type]?.add(Subscription(
                keys = keys,
                callback = callback,
                dataType = T::class
        ))
    }

    inline fun <reified T : Any> emit(type: EventType, data: T, keys: Set<String> = setOf()) {
        val toNotify = subscriptions[type]
                ?.filter { it.dataType == T::class }
                ?.filter {
                    subscriberKeysIntersectsWithEventKeys(it, keys)
                            .or(subscriberHasNoKeys(it))
                }

        toNotify?.forEach {
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