package org.hexworks.zircon.api.event

import org.hexworks.zircon.api.util.Identifier
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.api.util.Predicate

data class EventBusSubscription<in T : Event>(val callback: (T) -> Unit,
                                              val eventType: String,
                                              val identifier: Maybe<Identifier> = Maybe.empty()) : Subscription {

    fun hasIdentifier(identifier: Identifier) = this.identifier.filter(Predicate.isEqual(identifier)).isPresent

    override fun cancel() {
        EventBus.unsubscribe(this)
    }
}
