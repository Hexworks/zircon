package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.cobalt.databinding.api.createPropertyFrom
import org.hexworks.zircon.api.behavior.Hideable

class DefaultHideable(initialIsHidden: Boolean = false) : Hideable {

    override val hiddenProperty = createPropertyFrom(initialIsHidden)
    override var isHidden: Boolean by hiddenProperty.asDelegate()
}
