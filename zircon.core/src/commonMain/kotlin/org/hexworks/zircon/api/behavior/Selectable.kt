package org.hexworks.zircon.api.behavior

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.internal.behavior.impl.DefaultSelectable

/**
 * Represents an object that supports the notion of selection.
 */
public interface Selectable {

    public var isSelected: Boolean

    /**
     * A [Property] that wraps the [isSelected] value and offers data binding and
     * observability features.
     *
     * @see Property
     */
    public val selectedProperty: Property<Boolean>

    public companion object {

        /**
         * Creates a new [Selectable] with the default [initialSelected] value of `false`.
         */
        public fun create(initialSelected: Boolean = false): Selectable = create(initialSelected.toProperty())

        public fun create(selectedProperty: Property<Boolean>): Selectable = DefaultSelectable(selectedProperty)
    }
}
