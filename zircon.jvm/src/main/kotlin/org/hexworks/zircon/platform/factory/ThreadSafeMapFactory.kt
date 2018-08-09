package org.hexworks.zircon.platform.factory

import org.hexworks.zircon.internal.util.ThreadSafeMap
import org.hexworks.zircon.internal.util.DefaultThreadSafeMap

actual object ThreadSafeMapFactory {
    actual fun <K, V> create(): ThreadSafeMap<K, V> {
        return DefaultThreadSafeMap()
    }
}
