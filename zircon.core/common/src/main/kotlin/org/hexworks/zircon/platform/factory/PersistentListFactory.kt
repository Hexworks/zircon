package org.hexworks.zircon.platform.factory

import org.hexworks.zircon.internal.util.PersistentList
import org.hexworks.zircon.internal.util.PersistentMap
import org.hexworks.zircon.internal.util.PersistentSet

expect object PersistentListFactory {

    fun <E> create(): PersistentList<E>
}
