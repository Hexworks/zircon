package org.hexworks.zircon.platform.factory

import org.hexworks.zircon.internal.util.ThreadSafeMap

expect object ThreadSafeMapFactory {

    fun <K, V> create(): ThreadSafeMap<K, V>
}
