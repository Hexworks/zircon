package org.codetome.zircon.event

@Suppress("UNCHECKED_CAST")
object EventBus {

    @PublishedApi
    internal class Subscription<T : Any>(val keys: Set<String>,
                                         val callback: (Event<T>) -> Unit,
                                         val dataType: Class<T>)

    @PublishedApi
    internal val subscriptions: Map<EventType, MutableList<Subscription<*>>>
            = EventType.values()
            .map { Pair(it, mutableListOf<Subscription<*>>()) }
            .toMap()

    @JvmStatic
    inline fun <reified T : Any> subscribe(type: EventType,
                                           noinline callback: (Event<T>) -> Unit,
                                           keys: Set<String> = setOf()) {
        subscriptions[type]?.add(Subscription(
                keys = keys,
                callback = callback,
                dataType = T::class.java
        ))
    }

    @JvmStatic
    inline fun <reified T : Any> emit(type: EventType, data: T, keys: Set<String> = setOf()) {
        val toNotify = subscriptions[type]
                ?.filter {
                    it.dataType.isAssignableFrom(T::class.java)
                }
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