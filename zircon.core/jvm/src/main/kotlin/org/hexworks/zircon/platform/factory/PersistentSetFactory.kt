package org.hexworks.zircon.platform.factory

import kotlinx.collections.immutable.persistentSetOf
import org.hexworks.zircon.internal.util.DefaultPersistentSet
import org.hexworks.zircon.internal.util.PersistentSet

actual object PersistentSetFactory {

    actual fun <E> create(): PersistentSet<E> = DefaultPersistentSet(persistentSetOf())
}
