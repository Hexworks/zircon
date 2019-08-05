package org.hexworks.zircon.platform.factory

import org.hexworks.zircon.internal.util.PersistentMap
import org.hexworks.zircon.internal.util.PersistentSet

expect object PersistentSetFactory {

    fun <E> create(): PersistentSet<E>
}
