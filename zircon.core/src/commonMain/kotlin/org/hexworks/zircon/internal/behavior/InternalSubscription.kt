package org.hexworks.zircon.internal.behavior

import org.hexworks.cobalt.events.api.Subscription

public interface InternalSubscription<T : Any> : Subscription {

    public fun notify(event: T)
}
