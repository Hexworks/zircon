package org.hexworks.zircon.api.behavior

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.internal.behavior.impl.DefaultSelectable
import kotlin.jvm.JvmStatic

/**
 * Represents an object that supports the notion of selection.
 */
@Suppress("JVM_STATIC_IN_INTERFACE_1_6")
interface Selectable {

    var isSelected: Boolean

    /**
     * A [Property] that wraps the [isSelected] and offers data binding and
     * observability features.
     *
     * @see Property
     */
    val selectedProperty: Property<Boolean>

    companion object {

        /**
         * Creates a new [Selectable] with the default [initialSelected] value of `false`.
         */
        @JvmStatic
        fun create(initialSelected: Boolean = false): Selectable = DefaultSelectable(initialSelected)
    }
}
