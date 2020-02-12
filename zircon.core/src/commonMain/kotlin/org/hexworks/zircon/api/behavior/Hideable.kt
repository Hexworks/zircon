package org.hexworks.zircon.api.behavior

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.internal.behavior.impl.DefaultHideable
import kotlin.jvm.JvmStatic

/**
 * Represents an object which can be visually hidden (invisible).
 */
interface Hideable {

    var isHidden: Boolean

    val hiddenProperty: Property<Boolean>

    companion object {

        @JvmStatic
        fun create(initialIsHidden: Boolean = false): Hideable = DefaultHideable(initialIsHidden)
    }
}