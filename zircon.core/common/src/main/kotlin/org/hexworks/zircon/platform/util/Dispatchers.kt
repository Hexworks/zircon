package org.hexworks.zircon.platform.util

import kotlinx.coroutines.CoroutineDispatcher

expect object Dispatchers {

    /**
     * A coroutine dispatcher that is confined to a single thread. Useful for thread confinement.
     */
    val Single: CoroutineDispatcher
}
