package org.hexworks.zircon.internal.behavior

import kotlinx.collections.immutable.PersistentList
import org.hexworks.cobalt.events.api.Subscription

/**
 * Represents an object that can be observed for changes.
 */
public interface Observable<T : Any> {

    public val subscriptions: PersistentList<InternalSubscription<T>>

    public fun addObserver(callback: (T) -> Unit): Subscription

    public fun notifyObservers(event: T)
}
