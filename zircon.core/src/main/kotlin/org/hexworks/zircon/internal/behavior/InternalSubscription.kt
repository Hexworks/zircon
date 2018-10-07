package org.hexworks.zircon.internal.behavior

import org.hexworks.zircon.api.behavior.Subscription
import org.hexworks.zircon.api.event.Event

interface InternalSubscription<T : Event> : Subscription {

    fun notify(event: T)
}
