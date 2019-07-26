package org.hexworks.zircon.api.extensions

import org.hexworks.cobalt.databinding.api.event.ChangeEvent
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.behavior.ChangeListener
import org.hexworks.zircon.api.component.NumberInput

inline fun NumberInput.onValueChanged(crossinline fn: (ChangeEvent<Int>) -> Unit): Subscription {
    return onChange(object : ChangeListener<Int> {
        override fun onChange(event: ChangeEvent<Int>) {
            fn(event)
        }
    })
}