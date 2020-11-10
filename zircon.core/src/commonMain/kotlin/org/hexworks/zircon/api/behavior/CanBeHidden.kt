package org.hexworks.zircon.api.behavior

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.internal.behavior.impl.DefaultCanBeHidden
import kotlin.jvm.JvmStatic

/**
 * Represents an object which can be visually hidden (invisible).
 */
@Suppress("JVM_STATIC_IN_INTERFACE_1_6")
interface CanBeHidden : Hideable {

    override var isHidden: Boolean
    override val hiddenProperty: Property<Boolean>

    companion object {

        @JvmStatic
        fun create(initialIsHidden: Boolean = false): CanBeHidden = DefaultCanBeHidden(initialIsHidden)
    }
}
