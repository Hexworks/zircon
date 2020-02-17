package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.graphics.StyleSet

data class DefaultComponentStyleSet(
        private val styles: Map<ComponentState, StyleSet>
) : ComponentStyleSet {

    override val componentStateValue = ComponentState.DEFAULT.toProperty()
    override var componentState: ComponentState by componentStateValue.asDelegate()

    init {
        require(styles.size == ComponentState.values().size) {
            "Not all DefaultComponentStyleSet(s) have style information!"
        }
    }


    override fun fetchStyleFor(state: ComponentState) = styles[state] ?: error("")

    override fun currentStyle() = styles[componentState] ?: error("")

    override fun applyMouseOverStyle(): StyleSet {
        componentState = ComponentState.MOUSE_OVER
        return styles.getValue(componentState)
    }

    override fun applyActiveStyle(): StyleSet {
        componentState = ComponentState.ACTIVE
        return styles.getValue(componentState)
    }

    override fun applyFocusedStyle(): StyleSet {
        componentState = ComponentState.FOCUSED
        return styles.getValue(componentState)
    }

    override fun applyDisabledStyle(): StyleSet {
        componentState = ComponentState.DISABLED
        return styles.getValue(componentState)
    }

    override fun reset(): StyleSet {
        componentState = ComponentState.DEFAULT
        return styles.getValue(componentState)
    }
}
