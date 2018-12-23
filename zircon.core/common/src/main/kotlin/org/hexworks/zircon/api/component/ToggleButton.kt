package org.hexworks.zircon.api.component

import org.hexworks.cobalt.databinding.api.property.Property

interface ToggleButton : Component {

    val text: String
    val textProperty: Property<String>

    val isSelected: Boolean
    val selectedProperty: Property<Boolean>

}
