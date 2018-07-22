package org.codetome.zircon.platform.factory

import org.codetome.zircon.internal.util.ThreadSafeMap

expect object ThreadSafeMapFactory {

    fun <K, V> create(): ThreadSafeMap<K, V>
}
