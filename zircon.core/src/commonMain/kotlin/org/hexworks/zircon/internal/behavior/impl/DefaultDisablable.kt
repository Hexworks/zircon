package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.cobalt.databinding.api.createPropertyFrom
import org.hexworks.zircon.api.behavior.Disablable

class DefaultDisablable(initialEnabled: Boolean) : Disablable {

    override val disabledProperty = createPropertyFrom(initialEnabled)
    override var isDisabled: Boolean by disabledProperty.asDelegate()
}
