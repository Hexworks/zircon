package org.hexworks.zircon.platform.util

import kotlinx.coroutines.CoroutineDispatcher

actual object ZirconDispatchers {
    /**
     * Returns a single-threaded dispatcher.
     */
    actual fun single(): CoroutineDispatcher {
        TODO("not implemented")
    }

}
