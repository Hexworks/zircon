package org.codetome.zircon.util

actual object ThreadSafeMapFactory {
    actual fun <K, V> create(): ThreadSafeMap<K, V> {
        return JvmThreadSafeMap()
    }
}
