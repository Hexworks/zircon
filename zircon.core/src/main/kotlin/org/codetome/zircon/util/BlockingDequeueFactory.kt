package org.codetome.zircon.util

expect object BlockingDequeueFactory {

    fun <E> create(): BlockingDeque<E>
}
