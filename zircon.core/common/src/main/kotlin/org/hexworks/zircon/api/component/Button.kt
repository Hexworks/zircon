package org.hexworks.zircon.api.component

import org.hexworks.cobalt.databinding.api.value.ObservableValue
import org.hexworks.zircon.api.behavior.TextHolder

interface Button : Component, TextHolder {

    val isEnabled: Boolean
    val enabledValue: ObservableValue<Boolean>

    fun enable()

    fun disable()

}
