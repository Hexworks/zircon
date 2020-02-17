package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.graphics.StyleSet

/**
 * This interface represents a collection of [StyleSet]s which
 * will be used when a [Component]'s [ComponentState] changes.
 */
interface ComponentStyleSet {

    val isDefault
        get() = this == defaultStyleSet()

    /**
     * Returns the [StyleSet] for the given `state`.
     */
    fun fetchStyleFor(state: ComponentState): StyleSet

    companion object {

        fun defaultStyleSet() = ComponentStyleSetBuilder.newBuilder().build()

        fun empty() = ComponentStyleSetBuilder.newBuilder()
                .withDefaultStyle(StyleSet.empty())
                .build()
    }
}
