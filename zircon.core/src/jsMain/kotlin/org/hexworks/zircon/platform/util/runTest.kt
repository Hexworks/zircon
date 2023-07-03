package org.hexworks.zircon.platform.util

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise
import kotlin.coroutines.CoroutineContext

@OptIn(DelicateCoroutinesApi::class)
actual fun <T : Any> runTest(context: CoroutineContext, block: suspend () -> T) {
    GlobalScope.promise(context) {
        block()
    }
}
