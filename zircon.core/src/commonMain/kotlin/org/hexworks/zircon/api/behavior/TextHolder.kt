package org.hexworks.zircon.api.behavior

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.internal.behavior.impl.DefaultTextOverride
import kotlin.jvm.JvmStatic

@Suppress("JVM_STATIC_IN_INTERFACE_1_6")
@Deprecated("TextHolder was renamed to TextOverride, please use that instead. TextHolder will be removed in the next release")
interface TextHolder : HasText {

    override var text: String
    val textProperty: Property<String>

    companion object {

        @JvmStatic
        fun create(initialText: String = ""): TextOverride = DefaultTextOverride(initialText)
    }
}
