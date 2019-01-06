package org.hexworks.zircon.api.component

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.behavior.TextHolder

interface ToggleButton : Component, TextHolder {

    var isSelected: Boolean
    val selectedProperty: Property<Boolean>

}
