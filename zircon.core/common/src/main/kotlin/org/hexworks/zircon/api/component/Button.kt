package org.hexworks.zircon.api.component

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.behavior.TextHolder

/**
 * Generic clickable *button* component.
 */
interface Button : Component, TextHolder {

    val isEnabled: Boolean
    val enabledProperty: Property<Boolean>

    /**
     * Enables interaction with this [Button].
     */
    fun enable()

    /**
     * Disables interaction with this [Button].
     */
    fun disable()

}
