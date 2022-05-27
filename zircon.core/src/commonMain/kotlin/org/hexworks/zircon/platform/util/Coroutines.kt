package org.hexworks.zircon.platform.util

import kotlinx.coroutines.GlobalScope
import kotlin.coroutines.CoroutineContext

expect fun <T : Any> runTest(
    context: CoroutineContext = GlobalScope.coroutineContext,
    block: suspend () -> T
)
