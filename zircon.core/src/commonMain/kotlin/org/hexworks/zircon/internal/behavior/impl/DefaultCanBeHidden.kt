package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.behavior.CanBeHidden

class DefaultCanBeHidden(
    override val hiddenProperty: Property<Boolean>
) : CanBeHidden {
    override var isHidden: Boolean by hiddenProperty.asDelegate()
}
