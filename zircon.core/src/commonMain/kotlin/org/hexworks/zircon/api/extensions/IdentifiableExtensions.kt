@file:JvmName("IdentifiableUtils")
package org.hexworks.zircon.api.extensions

import org.hexworks.cobalt.core.api.UUID
import kotlin.jvm.JvmName

/**
 * Abbreviates this [UUID].
 */
fun UUID.abbreviate() = this.toString().subSequence(0, 4)
