package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.cobalt.databinding.api.extension.createPropertyFrom
import org.hexworks.zircon.api.behavior.CanBeHidden
import org.hexworks.zircon.api.behavior.Hideable

class DefaultCanBeHidden(initialIsHidden: Boolean = false) : CanBeHidden {

    override val hiddenProperty = createPropertyFrom(initialIsHidden)
    override var isHidden: Boolean by hiddenProperty.asDelegate()
}
