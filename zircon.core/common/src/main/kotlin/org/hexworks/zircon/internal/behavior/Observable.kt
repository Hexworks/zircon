package org.hexworks.zircon.internal.behavior


import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.internal.util.ThreadSafeQueue

interface Observable<T : Any> {

    val subscriptions: ThreadSafeQueue<InternalSubscription<T>>

    fun addObserver(callback: (T) -> Unit): Subscription

    fun notifyObservers(event: T)
}
