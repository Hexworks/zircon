package org.hexworks.zircon.internal.extensions

import org.hexworks.cobalt.events.api.Subscription

fun <T : Subscription> MutableList<T>.cancelAll() {
    forEach {
        it.cancel()
    }
    clear()
}
