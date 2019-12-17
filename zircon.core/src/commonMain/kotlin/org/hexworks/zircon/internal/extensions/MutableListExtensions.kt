package org.hexworks.zircon.internal.extensions

import org.hexworks.cobalt.databinding.api.binding.Binding
import org.hexworks.cobalt.events.api.Subscription

fun <T : Subscription> MutableList<T>.cancelAll() {
    forEach {
        it.cancel()
    }
    clear()
}

fun <T : Binding<Any>> MutableList<T>.disposeAll() {
    forEach {
        it.dispose()
    }
    clear()
}
