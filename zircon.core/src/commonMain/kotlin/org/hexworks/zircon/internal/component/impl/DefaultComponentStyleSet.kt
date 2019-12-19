package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.graphics.StyleSet

data class DefaultComponentStyleSet(private val styles: Map<ComponentState, StyleSet>)
    : ComponentStyleSet {

    private var currentState = ComponentState.DEFAULT

    init {
        require(styles.size == ComponentState.values().size) {
            "Not all DefaultComponentStyleSet(s) have style information!"
        }
    }

    override fun currentState() = currentState

    override fun fetchStyleFor(state: ComponentState) = styles[state] ?: error("")

    override fun currentStyle() = styles[currentState] ?: error("")

    override fun applyMouseOverStyle(): StyleSet {
        currentState = ComponentState.MOUSE_OVER
        return styles.getValue(currentState)
    }

    override fun applyActiveStyle(): StyleSet {
        currentState = ComponentState.ACTIVE
        return styles.getValue(currentState)
    }

    override fun applyFocusedStyle(): StyleSet {
        currentState = ComponentState.FOCUSED
        return styles.getValue(currentState)
    }

    override fun applyDisabledStyle(): StyleSet {
        currentState = ComponentState.DISABLED
        return styles.getValue(currentState)
    }

    override fun reset(): StyleSet {
        currentState = ComponentState.DEFAULT
        return styles.getValue(currentState)
    }
}
