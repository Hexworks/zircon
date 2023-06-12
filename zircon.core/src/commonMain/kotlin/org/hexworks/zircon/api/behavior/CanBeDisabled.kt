package org.hexworks.zircon.api.behavior

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.internal.behavior.impl.DefaultCanBeDisabled

/**
 * Represents an object which supports the notion of disabling. In the case of a component
 * this means that when it is disabled it can't be interacted with (click, press, etc).
 */
interface CanBeDisabled {

    var isDisabled: Boolean
    val disabledProperty: Property<Boolean>

    companion object {

        /**
         * Creates a new [CanBeDisabled] with the default [initialDisabled] value of `false`.
         */
        fun create(initialDisabled: Boolean = false): CanBeDisabled = DefaultCanBeDisabled(initialDisabled)
    }
}
