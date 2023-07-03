package org.hexworks.zircon.platform.util

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlin.coroutines.CoroutineContext

@OptIn(DelicateCoroutinesApi::class)
expect fun <T : Any> runTest(
    context: CoroutineContext = GlobalScope.coroutineContext,
    block: suspend () -> T
)
