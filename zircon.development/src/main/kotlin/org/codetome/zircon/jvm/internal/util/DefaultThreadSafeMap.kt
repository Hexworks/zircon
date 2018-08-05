package org.codetome.zircon.jvm.internal.util

import org.codetome.zircon.internal.util.ThreadSafeMap
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class DefaultThreadSafeMap<K, V>(private val backend: ConcurrentMap<K, V> = ConcurrentHashMap())
    : ThreadSafeMap<K, V>, MutableMap<K, V> by backend {

    override fun getOrDefault(key: K, defaultValue: V): V {
        return backend.getOrDefault(key, defaultValue)
    }
}
