package org.codetome.zircon.api.event

import org.codetome.zircon.api.util.Identifier
import org.codetome.zircon.api.util.Maybe
import org.codetome.zircon.api.util.Predicate
import org.codetome.zircon.internal.event.InternalEvent

data class Subscription<in T : Event>(val callback: (T) -> Unit,
                                                                                 val eventType: String,
                                                                                 val identifier: Maybe<Identifier> = Maybe.empty()) {

    fun hasIdentifier(identifier: Identifier) = this.identifier.filter(Predicate.isEqual(identifier)).isPresent
}
