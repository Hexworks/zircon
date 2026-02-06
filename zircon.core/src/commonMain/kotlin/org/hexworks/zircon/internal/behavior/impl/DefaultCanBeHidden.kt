package org.hexworks.zircon.internal.behavior.impl

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.behavior.CanBeHidden

class DefaultCanBeHidden(initialIsHidden: Boolean = false) : CanBeHidden {

    override val hiddenProperty = initialIsHidden.toProperty()
    override var isHidden: Boolean by hiddenProperty.asDelegate()
}
