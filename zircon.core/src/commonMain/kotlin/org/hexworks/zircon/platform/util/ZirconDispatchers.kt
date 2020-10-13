package org.hexworks.zircon.platform.util

import kotlinx.coroutines.CoroutineDispatcher

expect object ZirconDispatchers {

    fun single(): CoroutineDispatcher
}
