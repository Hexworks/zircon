package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.behavior.Selectable

class DefaultSelectable(initialSelected: Boolean) : Selectable {

    override val selectedProperty = initialSelected.toProperty()
    override var isSelected: Boolean by selectedProperty.asDelegate()
}
