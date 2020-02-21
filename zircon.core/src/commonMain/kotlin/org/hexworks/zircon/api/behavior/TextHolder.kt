package org.hexworks.zircon.api.behavior

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.internal.behavior.impl.DefaultTextHolder
import kotlin.jvm.JvmStatic

/**
 * Represents an object which holds [text].
 */
interface TextHolder {

    var text: String

    val textProperty: Property<String>

    companion object {

        @JvmStatic
        fun create(initialText: String = ""): TextHolder = DefaultTextHolder(initialText)
    }

}
