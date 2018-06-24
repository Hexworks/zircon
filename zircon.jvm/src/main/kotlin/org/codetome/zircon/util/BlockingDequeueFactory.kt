package org.codetome.zircon.util

actual object BlockingDequeueFactory {
    actual fun <E> create(): BlockingDeque<E> = JvmBlockingDeque()
}
