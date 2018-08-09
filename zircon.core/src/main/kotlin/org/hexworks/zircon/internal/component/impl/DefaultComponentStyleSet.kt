package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.component.ComponentState
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.graphics.StyleSet

data class DefaultComponentStyleSet(private val styles: Map<ComponentState, StyleSet>)
    : ComponentStyleSet {

    private var currentState = ComponentState.DEFAULT

    init {
        require(styles.size == ComponentState.values().size) {
            "Not all DefaultComponentStyleSet(s) have style information!"
        }
    }

    override fun getStyleFor(state: ComponentState) = styles[state]!!

    override fun getCurrentStyle() = styles[currentState]!!

    override fun mouseOver(): StyleSet {
        currentState = ComponentState.MOUSE_OVER
        return styles[currentState]!!
    }

    override fun activate(): StyleSet {
        currentState = ComponentState.ACTIVE
        return styles[currentState]!!
    }

    override fun giveFocus(): StyleSet {
        currentState = ComponentState.FOCUSED
        return styles[currentState]!!
    }

    override fun disable(): StyleSet {
        currentState = ComponentState.DISABLED
        return styles[currentState]!!
    }

    override fun reset(): StyleSet {
        currentState = ComponentState.DEFAULT
        return styles[currentState]!!
    }
}
