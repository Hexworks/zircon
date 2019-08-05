package org.hexworks.zircon.platform.factory

import org.hexworks.zircon.internal.util.PersistentMap

expect object PersistentMapFactory {

    fun <K, V> create(): PersistentMap<K, V>
}
