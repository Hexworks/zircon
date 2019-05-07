package org.hexworks.zircon.api.extensions

import org.hexworks.zircon.api.behavior.Disablable
import org.hexworks.zircon.api.uievent.Pass
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.api.uievent.UIEventResponse

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
