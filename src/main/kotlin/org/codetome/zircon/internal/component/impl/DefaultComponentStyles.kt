package org.codetome.zircon.internal.component.impl

import org.codetome.zircon.api.builder.StyleSetBuilder
import org.codetome.zircon.api.component.ComponentState
import org.codetome.zircon.api.component.ComponentStyles
import org.codetome.zircon.api.graphics.StyleSet

class DefaultComponentStyles(private val styles: Map<ComponentState, StyleSet>) : ComponentStyles {

    private var currentState = ComponentState.DEFAULT

    init {
        require(styles.size == ComponentState.values().size) {
            "Not all DefaultComponentStyles(s) have style information!"
        }
    }

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