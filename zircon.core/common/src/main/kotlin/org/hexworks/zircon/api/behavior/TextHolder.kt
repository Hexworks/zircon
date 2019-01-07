package org.hexworks.zircon.api.behavior

import org.hexworks.cobalt.databinding.api.event.ChangeListener
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.internal.behavior.impl.DefaultTextHolder

interface TextHolder {

    var text: String
    val textProperty: Property<String>

    fun onTextChanged(fn: ChangeListener<String>): Subscription {
        return textProperty.onChange(fn)
    }

    companion object {

        fun create(initialText: String = ""): TextHolder = DefaultTextHolder(initialText)
    }

}
