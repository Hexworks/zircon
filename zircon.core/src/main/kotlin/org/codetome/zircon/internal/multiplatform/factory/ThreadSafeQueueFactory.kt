package org.codetome.zircon.internal.multiplatform.factory

import org.codetome.zircon.internal.multiplatform.api.ThreadSafeQueue

expect object ThreadSafeQueueFactory {

    fun <E> create(): ThreadSafeQueue<E>
}
