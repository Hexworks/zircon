package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.graphics.StyleSet

/**
 * This interface represents a collection of [StyleSet]s which
 * will be used when a [Component]'s [ComponentState] changes.
 */
interface ComponentStyleSet {

    /**
     * Returns the style which is currently applied.
     */
    fun getCurrentStyle(): StyleSet

    /**
     * Returns the [StyleSet] for the given `state`.
     */
    fun getStyleFor(state: ComponentState): StyleSet

    /**
     * Applies the style for the `MOUSE_OVER` state.
     */
    fun applyMouseOverStyle(): StyleSet

    /**
     * Applies the style for the `ACTIVE` state.
     */
    fun applyActiveStyle(): StyleSet

    /**
     * Applies the style for the `FOCUSED` state.
     */
    fun applyFocusedStyle(): StyleSet

    /**
     * Applies the style for the `DISABLED` state.
     */
    fun applyDisabledStyle(): StyleSet

    /**
     * Resets the style to its initial state.
     */
    fun reset(): StyleSet

    companion object {

        fun defaultStyleSet() = ComponentStyleSetBuilder.newBuilder().build()
    }
}
