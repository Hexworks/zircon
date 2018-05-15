package org.codetome.zircon.internal.extensions

import java.util.*

/**
 * Returns an element of this [List] wrapped in an Maybe
 * which is empty if `idx` is out of bounds.
 */
fun <T> List<T>.getIfPresent(idx: Int) =
        if (idx >= size) {
            Optional.empty()
        } else {
            Optional.of(get(idx))
        }
