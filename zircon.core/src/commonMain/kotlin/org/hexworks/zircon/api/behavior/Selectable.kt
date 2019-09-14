package org.hexworks.zircon.api.behavior

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.internal.behavior.impl.DefaultSelectable

interface Selectable {

    var isSelected: Boolean
    val selectedProperty: Property<Boolean>

    fun onSelectionChanged(fn: ChangeListener<Boolean>): Subscription {
        return selectedProperty.onChange(fn::onChange)
    }

    companion object {

        fun create(initialSelected: Boolean = false): Selectable = DefaultSelectable(initialSelected)
    }
}
