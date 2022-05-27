package org.hexworks.zircon.platform.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors

actual object ZirconDispatchers {

    actual fun single(): CoroutineDispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
}
