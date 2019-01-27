package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.cobalt.databinding.api.createPropertyFrom
import org.hexworks.zircon.api.behavior.Selectable

class DefaultSelectable(initialSelected: Boolean) : Selectable {

    override val selectedProperty = createPropertyFrom(initialSelected)
    override var isSelected: Boolean by selectedProperty.asDelegate()
}
