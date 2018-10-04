package org.hexworks.zircon.api.event

import org.hexworks.zircon.api.util.Identifier
import org.hexworks.zircon.api.util.Maybe

data class EventBusSubscription<in T : Event>(val callback: (T) -> Unit,
                                              val eventType: String,
                                              val identifier: Maybe<Identifier> = Maybe.empty()) : Subscription {

    override fun cancel() {
        EventBus.unsubscribe(this)
    }
}
