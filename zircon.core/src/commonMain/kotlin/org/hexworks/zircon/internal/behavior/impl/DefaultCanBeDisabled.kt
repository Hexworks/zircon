package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.cobalt.databinding.api.extension.createPropertyFrom
import org.hexworks.zircon.api.behavior.CanBeDisabled
import org.hexworks.zircon.api.behavior.Disablable

class DefaultCanBeDisabled(initialEnabled: Boolean) : CanBeDisabled {

    override val disabledProperty = createPropertyFrom(initialEnabled)
    override var isDisabled: Boolean by disabledProperty.asDelegate()
}
