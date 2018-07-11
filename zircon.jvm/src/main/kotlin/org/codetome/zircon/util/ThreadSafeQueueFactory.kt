package org.codetome.zircon.util

actual object ThreadSafeQueueFactory {
    actual fun <E> create(): ThreadSafeQueue<E> = JvmThreadSafeQueue()
}
