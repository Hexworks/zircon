package org.hexworks.zircon.api.kotlin

import org.hexworks.cobalt.databinding.api.event.ChangeEvent
import org.hexworks.cobalt.databinding.api.event.ChangeListener
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.behavior.TextHolder

/**
 * Extension function which adapts [TextHolder.onTextChanged] to
 * Kotlin idioms (eg: lambdas).
 */
inline fun TextHolder.onTextChanged(crossinline fn: (ChangeEvent<String>) -> Unit): Subscription {
    return onTextChanged(object : ChangeListener<String> {
        override fun onChange(changeEvent: ChangeEvent<String>) {
            fn(changeEvent)
        }
    })
}
