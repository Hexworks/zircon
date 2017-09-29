package org.codetome.zircon.api.component

import org.codetome.zircon.api.graphics.StyleSet

/**
 * This interface represents a collection of [StyleSet]s which
 * will be used when a [Component]'s [ComponentState] this [ComponentStyles] is applied to
 * changes.
 */
interface ComponentStyles {

    fun getCurrentStyle(): StyleSet

    fun getStyleFor(state: ComponentState): StyleSet

    fun mouseOver(): StyleSet

    fun activate(): StyleSet

    fun giveFocus(): StyleSet

    fun disable(): StyleSet

    fun reset(): StyleSet
}