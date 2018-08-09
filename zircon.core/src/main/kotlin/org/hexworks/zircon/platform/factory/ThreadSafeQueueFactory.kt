package org.hexworks.zircon.platform.factory

import org.hexworks.zircon.internal.util.ThreadSafeQueue

expect object ThreadSafeQueueFactory {
    fun <E> create(): ThreadSafeQueue<E>
}
