package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.behavior.TextOverride

class DefaultTextOverride(override val textProperty: Property<String>) : TextOverride {

    override var text: String by textProperty.asDelegate()
}
