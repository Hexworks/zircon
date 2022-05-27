package org.hexworks.zircon.platform.util

import kotlin.coroutines.CoroutineContext

actual fun <T : Any> runTest(
    context: CoroutineContext,
    block: suspend () -> T
) {
    kotlinx.coroutines.runBlocking(context) { block() }
}
