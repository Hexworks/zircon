package org.hexworks.zircon.api.event

import org.hexworks.cobalt.datatypes.Identifier
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.behavior.Subscription

data class EventBusSubscription<in T : Event>(
        val callback: (T) -> Unit,
        val eventType: String,
        val identifier: Maybe<Identifier> = Maybe.empty()) : Subscription {

    override fun cancel() {
        EventBus.unsubscribe(this)
    }
}
