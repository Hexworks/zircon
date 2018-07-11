package org.codetome.zircon.util

expect object ThreadSafeQueueFactory {

    fun <E> create(): ThreadSafeQueue<E>
}
