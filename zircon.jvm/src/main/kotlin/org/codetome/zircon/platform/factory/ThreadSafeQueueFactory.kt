package org.codetome.zircon.platform.factory

import org.codetome.zircon.internal.util.ThreadSafeQueue
import org.codetome.zircon.internal.util.DefaultThreadSafeQueue

actual object ThreadSafeQueueFactory {
    actual fun <E> create(): ThreadSafeQueue<E> = DefaultThreadSafeQueue()
}
