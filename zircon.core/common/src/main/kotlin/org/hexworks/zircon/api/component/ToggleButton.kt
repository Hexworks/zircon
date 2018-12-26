package org.hexworks.zircon.api.component

import org.hexworks.cobalt.databinding.api.property.Property

interface ToggleButton : Component {

    var text: String
    val textProperty: Property<String>

    var isSelected: Boolean
    val selectedProperty: Property<Boolean>

}
