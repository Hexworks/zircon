package org.hexworks.zircon.api.extensions

import org.hexworks.cobalt.core.api.UUID

/**
 * Abbreviates this [UUID].
 */
public fun UUID.abbreviate(): CharSequence = this.toString().subSequence(0, 4)
