package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.behavior.CanBeDisabled

class DefaultCanBeDisabled(initialEnabled: Boolean) : CanBeDisabled {

    override val disabledProperty = initialEnabled.toProperty()
    override var isDisabled: Boolean by disabledProperty.asDelegate()
}
