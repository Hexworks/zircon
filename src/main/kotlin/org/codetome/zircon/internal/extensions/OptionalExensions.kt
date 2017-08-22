package org.codetome.zircon.internal.extensions

import java.util.*

/**
 * Negates `isPresent`.
 */
fun <T> Optional<T>.isNotPresent() = isPresent.not()