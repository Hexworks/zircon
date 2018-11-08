package org.hexworks.zircon.internal.util

interface ThreadSafeMap<K, V> : MutableMap<K, V> {

    fun getOrDefault(key: K, defaultValue: V): V
}
