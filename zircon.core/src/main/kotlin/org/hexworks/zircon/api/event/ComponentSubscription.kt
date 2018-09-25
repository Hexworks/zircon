package org.hexworks.zircon.api.event

import org.hexworks.zircon.api.listener.InputListener
import org.hexworks.zircon.internal.util.ThreadSafeQueue

class ComponentSubscription(
        val listener: InputListener,
        private val subscriptions: ThreadSafeQueue<out Subscription>) : Subscription {

    override fun cancel() {
        subscriptions.remove(this)
    }
}
