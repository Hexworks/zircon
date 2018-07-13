package org.codetome.zircon.internal.multiplatform.factory

import org.codetome.zircon.internal.multiplatform.impl.JvmThreadSafeMap
import org.codetome.zircon.internal.multiplatform.api.ThreadSafeMap

actual object ThreadSafeMapFactory {
    actual fun <K, V> create(): ThreadSafeMap<K, V> {
        return JvmThreadSafeMap()
    }
}
