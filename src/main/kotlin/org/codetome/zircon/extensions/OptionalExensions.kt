package org.codetome.zircon.extensions

import java.util.*

/**
 * Negates `isPresent`.
 */
fun <T> Optional<T>.isNotPresent() = isPresent.not()