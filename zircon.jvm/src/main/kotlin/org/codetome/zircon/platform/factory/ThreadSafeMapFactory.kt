package org.codetome.zircon.platform.factory

import org.codetome.zircon.internal.util.DefaultThreadSafeMap
import org.codetome.zircon.internal.util.ThreadSafeMap

actual object ThreadSafeMapFactory {
    actual fun <K, V> create(): ThreadSafeMap<K, V> {
        return DefaultThreadSafeMap()
    }
}
