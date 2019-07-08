package org.hexworks.zircon.platform.util

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.asCoroutineDispatcher
import java.util.concurrent.Executors

actual object Dispatchers {

    actual val Single: CoroutineDispatcher = Executors.newSingleThreadExecutor().asCoroutineDispatcher()
}
