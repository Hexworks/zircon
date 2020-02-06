@file:JvmName("IdentifiableUtils")
package org.hexworks.zircon.api.extensions

import org.hexworks.cobalt.core.api.Identifier
import kotlin.jvm.JvmName

/**
 * Abbreviates this [Identifier].
 */
fun Identifier.abbreviate() = this.toString().subSequence(0, 4)
