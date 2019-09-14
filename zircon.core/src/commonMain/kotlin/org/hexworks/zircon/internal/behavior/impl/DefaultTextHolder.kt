package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.cobalt.databinding.api.createPropertyFrom
import org.hexworks.zircon.api.behavior.TextHolder

class DefaultTextHolder(initialText: String) : TextHolder {

    override val textProperty = createPropertyFrom(initialText)
    override var text: String by textProperty.asDelegate()
}
