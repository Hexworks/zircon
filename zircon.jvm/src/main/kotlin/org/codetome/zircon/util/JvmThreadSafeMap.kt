package org.codetome.zircon.util

import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class JvmThreadSafeMap<K, V>(private val backend: ConcurrentMap<K, V> = ConcurrentHashMap())
    : ThreadSafeMap<K, V>, MutableMap<K, V> by backend
