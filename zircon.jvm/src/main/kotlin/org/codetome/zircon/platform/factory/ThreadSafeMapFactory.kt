package org.codetome.zircon.platform.factory

import org.codetome.zircon.internal.util.ThreadSafeMap
import org.codetome.zircon.internal.util.DefaultThreadSafeMap

actual object ThreadSafeMapFactory {
    actual fun <K, V> create(): ThreadSafeMap<K, V> {
        return DefaultThreadSafeMap()
    }
}
