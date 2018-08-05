package org.codetome.zircon.jvm.internal.util

import org.codetome.zircon.internal.util.TreeMap


class DefaultTreeMap<K, V>(private val backend: java.util.TreeMap<K, V> = java.util.TreeMap())
    : TreeMap<K, V>, MutableMap<K, V> by backend {

    override fun getOrDefault(key: K, defaultValue: V): V {
        return backend.getOrDefault(key, defaultValue)
    }
}
