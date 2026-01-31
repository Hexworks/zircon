package org.hexworks.zircon.api.behavior

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.internal.behavior.impl.DefaultCanBeHidden

/**
 * Represents an object which can be visually hidden (invisible).
 */
interface CanBeHidden {

    var isHidden: Boolean
    val hiddenProperty: Property<Boolean>

    companion object {

        fun create(initialIsHidden: Boolean = false): CanBeHidden = DefaultCanBeHidden(initialIsHidden)
    }
}
