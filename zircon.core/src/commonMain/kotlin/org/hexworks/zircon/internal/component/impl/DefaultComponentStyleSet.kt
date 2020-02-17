package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.component.data.ComponentState.values
import org.hexworks.zircon.api.graphics.StyleSet

data class DefaultComponentStyleSet(
        private val styles: Map<ComponentState, StyleSet>
) : ComponentStyleSet {

    init {
        require(styles.size == values().size) {
            "Not all DefaultComponentStyleSet(s) have style information!"
        }
    }

    override fun fetchStyleFor(state: ComponentState) = styles[state] ?: error("")
}
