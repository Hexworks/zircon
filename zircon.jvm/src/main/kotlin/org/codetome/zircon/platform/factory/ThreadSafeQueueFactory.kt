package org.codetome.zircon.platform.factory

import org.codetome.zircon.internal.util.DefaultThreadSafeQueue
import org.codetome.zircon.internal.util.ThreadSafeQueue

actual object ThreadSafeQueueFactory {
    actual fun <E> create(): ThreadSafeQueue<E> = DefaultThreadSafeQueue()
}
