package org.hexworks.zircon.api.behavior

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.internal.behavior.impl.DefaultCanBeDisabled

/**
 * Represents an object that supports the notion of disabling. In the case of a component
 * this means that when it is disabled, it can't be interacted with (click, press, etc.).
 */
public interface CanBeDisabled {

    public var isDisabled: Boolean
    public val disabledProperty: Property<Boolean>

    public companion object {

        /**
         * Creates a new [CanBeDisabled] with the default [initialDisabled] value of `false`.
         */
        public fun create(initialDisabled: Boolean = false): CanBeDisabled = create(initialDisabled.toProperty())

        public fun create(disabledProperty: Property<Boolean>): CanBeDisabled = DefaultCanBeDisabled(disabledProperty)
    }
}
