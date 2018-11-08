package org.hexworks.zircon.internal.extensions

import org.hexworks.zircon.api.util.Maybe

/**
 * Returns an element of this [StringBuilder] wrapped in an Maybe
 * which is empty if `idx` is out of bounds.
 */
fun StringBuilder.getIfPresent(idx: Int) =
        if (idx >= length) {
            Maybe.empty()
        } else {
            Maybe.of(get(idx))
        }
