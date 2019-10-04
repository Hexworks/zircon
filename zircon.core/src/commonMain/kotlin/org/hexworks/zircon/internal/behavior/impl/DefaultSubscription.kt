package org.hexworks.zircon.internal.behavior.impl


import org.hexworks.cobalt.events.api.CancelState
import org.hexworks.cobalt.events.api.NotCancelled
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.internal.behavior.InternalSubscription
import org.hexworks.zircon.internal.util.PersistentList

class DefaultSubscription<T : Any>(
        val listener: (T) -> Unit,
        private var subscriptions: PersistentList<Subscription>) : InternalSubscription<T> {

    override val cancelState: CancelState = NotCancelled

    override fun notify(event: T) {
        listener(event)
    }

    override fun cancel(cancelState: CancelState) {
        subscriptions = subscriptions.remove(this)
    }
}
