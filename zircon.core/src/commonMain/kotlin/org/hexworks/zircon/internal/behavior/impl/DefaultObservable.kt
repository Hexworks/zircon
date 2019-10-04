package org.hexworks.zircon.internal.behavior.impl


import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.internal.behavior.InternalSubscription
import org.hexworks.zircon.internal.behavior.Observable
import org.hexworks.zircon.platform.factory.PersistentListFactory

class DefaultObservable<T : Any> : Observable<T> {

    override var subscriptions = PersistentListFactory.create<InternalSubscription<T>>()
        private set

    override fun addObserver(callback: (T) -> Unit): Subscription {
        return DefaultSubscription(callback, subscriptions).also {
            subscriptions = subscriptions.add(it)
        }
    }

    override fun notifyObservers(event: T) {
        subscriptions.forEach {
            it.notify(event)
        }
    }
}
