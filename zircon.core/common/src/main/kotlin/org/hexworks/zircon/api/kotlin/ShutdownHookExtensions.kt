package org.hexworks.zircon.api.kotlin

import org.hexworks.zircon.api.behavior.ShutdownHook
import org.hexworks.zircon.api.util.Runnable

/**
 * Extension function which adapts [ShutdownHook.onShutdown] to
 * Kotlin idioms (eg: lambdas).
 */
inline fun ShutdownHook.onShutdown(crossinline listener: () -> Unit) = onShutdown(object : Runnable {
    override fun run() {
        listener.invoke()
    }
})
