package org.codetome.zircon.internal.multiplatform.factory

import org.codetome.zircon.internal.multiplatform.impl.JvmThreadSafeQueue
import org.codetome.zircon.internal.multiplatform.api.ThreadSafeQueue

actual object ThreadSafeQueueFactory {
    actual fun <E> create(): ThreadSafeQueue<E> = JvmThreadSafeQueue()
}
