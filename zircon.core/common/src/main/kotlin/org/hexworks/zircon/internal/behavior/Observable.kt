package org.hexworks.zircon.internal.behavior

import org.hexworks.zircon.api.behavior.Subscription
import org.hexworks.zircon.api.event.Event
import org.hexworks.zircon.api.util.Consumer
import org.hexworks.zircon.internal.util.ThreadSafeQueue

interface Observable<T : Any> {

    val subscriptions: ThreadSafeQueue<InternalSubscription<T>>

    fun addObserver(callback: Consumer<T>): Subscription

    fun notifyObservers(event: T)
}
