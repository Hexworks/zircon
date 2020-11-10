package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.cobalt.databinding.api.extension.createPropertyFrom
import org.hexworks.zircon.api.behavior.TextOverride

class DefaultTextOverride(initialText: String) : TextOverride {

    override val textProperty = createPropertyFrom(initialText)
    override var text: String by textProperty.asDelegate()
}
