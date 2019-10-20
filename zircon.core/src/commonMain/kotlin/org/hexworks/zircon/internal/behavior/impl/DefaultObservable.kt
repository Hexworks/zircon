package org.hexworks.zircon.internal.behavior.impl


import kotlinx.collections.immutable.persistentListOf
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.internal.behavior.InternalSubscription
import org.hexworks.zircon.internal.behavior.Observable

class DefaultObservable<T : Any> : Observable<T> {

    override var subscriptions = persistentListOf<InternalSubscription<T>>()
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
