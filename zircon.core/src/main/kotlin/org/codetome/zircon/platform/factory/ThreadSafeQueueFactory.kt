package org.codetome.zircon.platform.factory

import org.codetome.zircon.internal.util.ThreadSafeQueue

expect object ThreadSafeQueueFactory {

    fun <E> create(): ThreadSafeQueue<E>
}
