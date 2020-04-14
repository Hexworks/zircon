package org.hexworks.zircon.internal.extensions

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf

public inline fun <T, R> PersistentList<T>.flatMap(transform: (T) -> PersistentList<R>): PersistentList<R> {
    var result = persistentListOf<R>()
    forEach { element ->
        result = result.addAll(transform(element))
    }
    return result
}