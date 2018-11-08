package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.cobalt.datatypes.sam.Consumer
import org.hexworks.zircon.api.behavior.Subscription
import org.hexworks.zircon.internal.behavior.InternalSubscription
import org.hexworks.zircon.internal.util.ThreadSafeQueue

class DefaultSubscription<T : Any>(
        val listener: Consumer<T>,
        private val subscriptions: ThreadSafeQueue<out Subscription>) : InternalSubscription<T> {

    override fun notify(event: T) {
        listener.accept(event)
    }

    override fun cancel() {
        subscriptions.remove(this)
    }
}
