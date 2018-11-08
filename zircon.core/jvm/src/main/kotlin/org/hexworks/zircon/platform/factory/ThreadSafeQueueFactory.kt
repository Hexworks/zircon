package org.hexworks.zircon.platform.factory

import org.hexworks.zircon.internal.util.ThreadSafeQueue
import org.hexworks.zircon.internal.util.DefaultThreadSafeQueue

actual object ThreadSafeQueueFactory {
    actual fun <E> create(): ThreadSafeQueue<E> = DefaultThreadSafeQueue()
}
