package org.hexworks.zircon.internal.behavior


import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.internal.util.PersistentList

interface Observable<T : Any> {

    val subscriptions: PersistentList<InternalSubscription<T>>

    fun addObserver(callback: (T) -> Unit): Subscription

    fun notifyObservers(event: T)
}
