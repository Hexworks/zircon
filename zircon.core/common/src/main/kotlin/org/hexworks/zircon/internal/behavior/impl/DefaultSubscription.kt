package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.cobalt.datatypes.sam.Consumer
import org.hexworks.cobalt.events.api.CancelState
import org.hexworks.cobalt.events.api.NotCancelled
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.internal.behavior.InternalSubscription
import org.hexworks.zircon.internal.util.ThreadSafeQueue

class DefaultSubscription<T : Any>(
        val listener: Consumer<T>,
        private val subscriptions: ThreadSafeQueue<out Subscription>) : InternalSubscription<T> {

    override val cancelState: CancelState = NotCancelled

    override fun notify(event: T) {
        listener.accept(event)
    }

    override fun cancel(cancelState: CancelState) {
        subscriptions.remove(this)
    }
}
