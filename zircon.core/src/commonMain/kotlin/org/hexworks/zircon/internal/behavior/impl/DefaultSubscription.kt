package org.hexworks.zircon.internal.behavior.impl

import kotlinx.collections.immutable.PersistentList
import org.hexworks.cobalt.core.behavior.DisposeState
import org.hexworks.cobalt.core.behavior.NotDisposed
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.internal.behavior.InternalSubscription

class DefaultSubscription<T : Any>(
        val listener: (T) -> Unit,
        private var subscriptions: PersistentList<Subscription>
) : InternalSubscription<T> {

    override val disposeState: DisposeState = NotDisposed

    override fun notify(event: T) {
        listener(event)
    }

    override fun dispose(disposeState: DisposeState) {
        subscriptions = subscriptions.remove(this)
    }
}
