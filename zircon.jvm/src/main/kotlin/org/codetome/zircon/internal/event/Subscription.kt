package org.codetome.zircon.internal.event

import org.codetome.zircon.internal.util.Identifier
import java.util.*

data class Subscription<in T : Event>(val callback: (T) -> Unit,
                                      val eventType: String,
                                      val identifier: Optional<Identifier> = Optional.empty()) {

    fun hasIdentifier(identifier: Identifier) = this.identifier.filter { it == identifier }.isPresent
}
