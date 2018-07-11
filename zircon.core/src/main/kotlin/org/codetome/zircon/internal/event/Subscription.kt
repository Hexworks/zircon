package org.codetome.zircon.internal.event

import org.codetome.zircon.internal.util.Identifier
import org.codetome.zircon.internal.multiplatform.api.Maybe
import org.codetome.zircon.internal.multiplatform.api.Predicate

data class Subscription<in T : Event>(val callback: (T) -> Unit,
                                      val eventType: String,
                                      val identifier: Maybe<Identifier> = Maybe.empty()) {

    fun hasIdentifier(identifier: Identifier) = this.identifier.filter(Predicate.isEqual(identifier)).isPresent
}
