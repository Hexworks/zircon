package org.hexworks.zircon.api.behavior

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.internal.behavior.impl.DefaultCanBeHidden

/**
 * Represents an object which can be visually hidden (invisible).
 */
public interface CanBeHidden {

    public var isHidden: Boolean
    public val hiddenProperty: Property<Boolean>

    public companion object {

        public fun create(initialIsHidden: Boolean = false): CanBeHidden = create(initialIsHidden.toProperty())

        public fun create(hiddenProperty: Property<Boolean>): CanBeHidden = DefaultCanBeHidden(hiddenProperty)
    }
}
