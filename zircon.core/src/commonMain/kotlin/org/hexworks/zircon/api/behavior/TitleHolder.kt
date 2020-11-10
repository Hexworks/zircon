package org.hexworks.zircon.api.behavior

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.internal.behavior.impl.DefaultTitleOverride
import kotlin.jvm.JvmStatic

@Deprecated("TitleHolder was renamed to TitleOverride, please use that instead. TitleHolder will be removed in the next release")
@Suppress("JVM_STATIC_IN_INTERFACE_1_6")
interface TitleHolder : HasTitle {

    override var title: String

    val titleProperty: Property<String>

    companion object {

        @JvmStatic
        fun create(initialTitle: String = ""): TitleOverride = DefaultTitleOverride(initialTitle)
    }
}
