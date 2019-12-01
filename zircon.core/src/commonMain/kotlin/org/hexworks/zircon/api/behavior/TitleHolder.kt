package org.hexworks.zircon.api.behavior

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.internal.behavior.impl.DefaultTitleHolder
import kotlin.jvm.JvmStatic

/**
 * Represents an object which has a [title].
 */
interface TitleHolder {

    var title: String
    val titleProperty: Property<String>

    companion object {

        @JvmStatic
        fun create(initialTitle: String = ""): TitleHolder = DefaultTitleHolder(initialTitle)
    }
}
