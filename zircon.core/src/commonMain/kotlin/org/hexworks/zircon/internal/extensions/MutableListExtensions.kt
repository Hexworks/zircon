package org.hexworks.zircon.internal.extensions

import org.hexworks.cobalt.core.api.behavior.Disposable

fun <T : Disposable> MutableList<T>.disposeAll() {
    forEach {
        it.dispose()
    }
    clear()
}
