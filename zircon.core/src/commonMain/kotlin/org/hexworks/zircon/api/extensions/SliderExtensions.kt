package org.hexworks.zircon.api.extensions

import org.hexworks.cobalt.databinding.api.event.ChangeEvent
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.behavior.ChangeListener
import org.hexworks.zircon.api.component.Slider

inline fun Slider.onValueChanged(crossinline fn: (ChangeEvent<Int>) -> Unit): Subscription {
    return onValueChange(object : ChangeListener<Int> {
        override fun onChange(event: ChangeEvent<Int>) {
            fn(event)
        }
    })
}

inline fun Slider.onStepChanged(crossinline fn: (ChangeEvent<Int>) -> Unit): Subscription {
    return onStepChange(object : ChangeListener<Int> {
        override fun onChange(event: ChangeEvent<Int>) {
            fn(event)
        }
    })
}