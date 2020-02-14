package org.hexworks.zircon.internal.extensions

import org.hexworks.cobalt.core.behavior.Disposable

fun <T : Disposable> MutableList<T>.disposeAll() {
    forEach {
        it.dispose()
    }
    clear()
}
