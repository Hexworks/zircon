package org.hexworks.zircon.internal.behavior

import org.hexworks.zircon.api.behavior.Subscription

interface InternalSubscription<T : Any> : Subscription {

    fun notify(event: T)
}
