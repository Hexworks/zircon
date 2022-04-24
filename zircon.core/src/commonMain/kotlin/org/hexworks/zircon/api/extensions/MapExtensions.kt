package org.hexworks.zircon.api.extensions

fun <K, V> Map<K, V>.getOrDefault(key: K, defaultValue: V): V {
    return this.getOrDefault(key, defaultValue)
}
