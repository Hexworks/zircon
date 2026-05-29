package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.behavior.Selectable

class DefaultSelectable(
    override val selectedProperty: Property<Boolean>,
) : Selectable {
    override var isSelected: Boolean by selectedProperty.asDelegate()
}
