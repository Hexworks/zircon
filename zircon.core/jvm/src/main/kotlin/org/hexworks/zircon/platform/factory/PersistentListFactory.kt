package org.hexworks.zircon.platform.factory

import kotlinx.collections.immutable.persistentListOf
import org.hexworks.zircon.internal.util.DefaultPersistentList
import org.hexworks.zircon.internal.util.PersistentList

actual object PersistentListFactory {

    actual fun <E> create(): PersistentList<E> = DefaultPersistentList(persistentListOf())

}
