package org.hexworks.zircon.api.component

import org.hexworks.cobalt.databinding.api.property.Property

interface Button : Component {

    val text: String

    val textProperty: Property<String>
}
