package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.behavior.CanBeDisabled

class DefaultCanBeDisabled(
    override val disabledProperty: Property<Boolean>
) : CanBeDisabled {
    override var isDisabled: Boolean by disabledProperty.asDelegate()
}
