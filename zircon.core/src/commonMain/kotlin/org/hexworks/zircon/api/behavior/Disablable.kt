package org.hexworks.zircon.api.behavior

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.internal.behavior.impl.DefaultCanBeDisabled
import kotlin.jvm.JvmStatic

@Suppress("JVM_STATIC_IN_INTERFACE_1_6")
@Deprecated("This class was renamed to CanBeDisabled, please use that instead. Disablable will be removed in the next release")
interface Disablable {

    var isDisabled: Boolean
    val disabledProperty: Property<Boolean>

    companion object {

        @JvmStatic
        fun create(initialDisabled: Boolean = false): Disablable = DefaultCanBeDisabled(initialDisabled)
    }
}
