package org.hexworks.zircon.api.behavior

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.internal.behavior.impl.DefaultDisablable

/**
 * Represents an object which supports the notion of disabling.
 * In the case of a component this means that when it is disabled
 * it can't be interacted with (click, press, etc).
 */
interface Disablable {

    var isDisabled: Boolean
    val disabledProperty: Property<Boolean>

    companion object {

        fun create(initialDisabled: Boolean = false): Disablable = DefaultDisablable(initialDisabled)
    }
}
