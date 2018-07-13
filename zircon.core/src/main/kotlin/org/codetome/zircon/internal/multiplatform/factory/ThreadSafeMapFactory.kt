package org.codetome.zircon.internal.multiplatform.factory

import org.codetome.zircon.internal.multiplatform.api.ThreadSafeMap

expect object ThreadSafeMapFactory {

    fun <K, V> create(): ThreadSafeMap<K, V>
}
