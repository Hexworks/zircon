package org.codetome.zircon.platform.factory

import org.codetome.zircon.jvm.internal.util.DefaultThreadSafeMap
import org.codetome.zircon.internal.util.ThreadSafeMap

object ThreadSafeMapFactory {
    fun <K, V> create(): ThreadSafeMap<K, V> {
        return DefaultThreadSafeMap()
    }
}
