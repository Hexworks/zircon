@file:JvmName("DisablableUtils")

package org.hexworks.zircon.api.extensions

import org.hexworks.cobalt.databinding.api.event.ChangeEvent
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.behavior.Disablable
import org.hexworks.zircon.api.uievent.Pass
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.api.uievent.UIEventResponse
import kotlin.jvm.JvmName

fun Disablable.onDisabledChanged(fn: (ChangeEvent<Boolean>) -> Unit): Subscription {
    return disabledProperty.onChange(fn)
}

var Disablable.isEnabled: Boolean
    get() = isDisabled.not()
    set(value) {
        isDisabled = value.not()
    }

internal fun Disablable.whenEnabled(fn: () -> Unit): UIEventResponse {
    return if (isDisabled) Pass else {
        fn()
        Processed
    }
}

internal fun Disablable.whenEnabledRespondWith(fn: () -> UIEventResponse): UIEventResponse {
    return if (isDisabled) Pass else {
        fn()
    }
}
