package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.cobalt.databinding.api.createPropertyFrom
import org.hexworks.zircon.api.behavior.Selectable

class DefaultSelectable : Selectable {

    override val selectedProperty = createPropertyFrom(false)
    override var isSelected: Boolean by selectedProperty.asDelegate()
}
