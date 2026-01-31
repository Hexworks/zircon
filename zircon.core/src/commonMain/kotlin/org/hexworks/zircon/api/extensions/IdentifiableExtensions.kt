package org.hexworks.zircon.api.extensions

import org.hexworks.cobalt.core.api.UUID

/**
 * Abbreviates this [UUID].
 */
fun UUID.abbreviate() = this.toString().subSequence(0, 4)
