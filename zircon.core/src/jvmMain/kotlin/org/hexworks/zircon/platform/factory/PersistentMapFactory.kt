package org.hexworks.zircon.platform.factory

import kotlinx.collections.immutable.persistentMapOf
import org.hexworks.zircon.internal.util.DefaultPersistentMap
import org.hexworks.zircon.internal.util.PersistentMap

actual object PersistentMapFactory {

    actual fun <K, V> create(): PersistentMap<K, V> = DefaultPersistentMap(persistentMapOf())
}
