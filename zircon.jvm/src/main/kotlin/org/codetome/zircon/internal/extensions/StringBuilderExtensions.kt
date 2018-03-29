package org.codetome.zircon.internal.extensions

import java.util.*

/**
 * Returns an element of this [StringBuilder] wrapped in an Optional
 * which is empty if `idx` is out of bounds.
 */
fun StringBuilder.getIfPresent(idx: Int) =
        if (idx >= length) {
            Optional.empty()
        } else {
            Optional.of(get(idx))
        }