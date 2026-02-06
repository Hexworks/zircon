package org.hexworks.zircon.internal.behavior

import kotlinx.collections.immutable.PersistentList
import org.hexworks.cobalt.events.api.Subscription

/**
 * Represents an object that can be observed for changes.
 */
interface Observable<T : Any> {

    val subscriptions: PersistentList<InternalSubscription<T>>

    fun addObserver(callback: (T) -> Unit): Subscription

    fun notifyObservers(event: T)
}
