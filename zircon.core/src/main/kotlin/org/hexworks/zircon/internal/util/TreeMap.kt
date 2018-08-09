package org.hexworks.zircon.internal.util

interface TreeMap<K, V> : MutableMap<K, V> {


    fun getOrDefault(key: K, defaultValue: V): V
}
