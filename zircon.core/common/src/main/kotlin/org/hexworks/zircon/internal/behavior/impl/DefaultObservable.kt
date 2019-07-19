package org.hexworks.zircon.internal.behavior.impl


import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.internal.behavior.InternalSubscription
import org.hexworks.zircon.internal.behavior.Observable
import org.hexworks.zircon.platform.factory.ThreadSafeQueueFactory

class DefaultObservable<T : Any> : Observable<T> {

    override val subscriptions = ThreadSafeQueueFactory.create<InternalSubscription<T>>()

    override fun addObserver(callback: (T) -> Unit): Subscription {
        return DefaultSubscription(callback, subscriptions).also {
            subscriptions.add(it)
        }
    }

    override fun notifyObservers(event: T) {
        subscriptions.forEach {
            it.notify(event)
        }
    }
}
