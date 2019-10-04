package org.hexworks.zircon.internal.behavior

import org.hexworks.cobalt.events.api.Subscription

interface InternalSubscription<T : Any> : Subscription {

    fun notify(event: T)
}
