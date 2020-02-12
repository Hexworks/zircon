package org.hexworks.zircon.api.behavior

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.internal.behavior.impl.DefaultSelectable
import kotlin.jvm.JvmStatic

/**
 * Represents an object which can be selected.
 */
interface Selectable {

    var isSelected: Boolean

    val selectedProperty: Property<Boolean>

    companion object {

        @JvmStatic
        fun create(initialSelected: Boolean = false): Selectable = DefaultSelectable(initialSelected)
    }
}
