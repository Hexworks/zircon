package org.hexworks.zircon.api.component

import org.hexworks.cobalt.databinding.api.property.Property

interface Panel : Container {

    val title: String
    val titleProperty: Property<String>
}
