package org.codetome.zircon.platform.factory

import org.codetome.zircon.internal.util.DefaultThreadSafeQueue
import org.codetome.zircon.internal.util.ThreadSafeQueue

object ThreadSafeQueueFactory {
    fun <E> create(): ThreadSafeQueue<E> = DefaultThreadSafeQueue()
}
