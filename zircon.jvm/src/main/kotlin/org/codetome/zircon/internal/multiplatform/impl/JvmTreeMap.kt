package org.codetome.zircon.internal.multiplatform.impl

import java.util.*

class JvmTreeMap<K, V>(private val backend: TreeMap<K, V> = TreeMap())
    : org.codetome.zircon.internal.multiplatform.api.TreeMap<K, V>, MutableMap<K, V> by backend {

    override fun getOrDefault(key: K, defaultValue: V): V {
        return backend.getOrDefault(key, defaultValue)
    }
}
