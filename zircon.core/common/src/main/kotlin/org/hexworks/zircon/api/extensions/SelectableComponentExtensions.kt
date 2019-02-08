package org.hexworks.zircon.api.extensions

import org.hexworks.cobalt.databinding.api.event.ChangeEvent
import org.hexworks.cobalt.databinding.api.event.ChangeListener
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.behavior.Selectable

/**
 * Extension function which adapts [Selectable.onSelectionChanged] to
 * Kotlin idioms (eg: lambdas).
 */
inline fun Selectable.onSelectionChanged(crossinline fn: (ChangeEvent<Boolean>) -> Unit): Subscription {
    return onSelectionChanged(object : ChangeListener<Boolean> {
        override fun onChange(changeEvent: ChangeEvent<Boolean>) {
            fn(changeEvent)
        }
    })
}
