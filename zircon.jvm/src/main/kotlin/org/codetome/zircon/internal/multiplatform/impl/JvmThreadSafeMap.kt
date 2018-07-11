package org.codetome.zircon.internal.multiplatform.impl

import org.codetome.zircon.internal.multiplatform.api.ThreadSafeMap
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

class JvmThreadSafeMap<K, V>(private val backend: ConcurrentMap<K, V> = ConcurrentHashMap())
    : ThreadSafeMap<K, V>, MutableMap<K, V> by backend
