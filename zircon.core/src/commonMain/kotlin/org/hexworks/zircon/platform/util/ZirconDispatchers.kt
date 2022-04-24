package org.hexworks.zircon.platform.util

import kotlinx.coroutines.CoroutineDispatcher

expect object ZirconDispatchers {

    /**
     * Returns a single-threaded dispatcher.
     */
    fun single(): CoroutineDispatcher
}
