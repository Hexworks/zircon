package org.codetome.zircon.util

expect object ThreadSafeMapFactory {

    fun <K, V> create(): ThreadSafeMap<K, V>
}
