package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.builder.component.componentStyleSet
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.graphics.StyleSet

/**
 * This interface represents a collection of [StyleSet]s which
 * will be used when a [Component]'s [ComponentState] changes.
 * @see ComponentState
 */
//! TODO: Rename this
interface ComponentStyleSet {

    /**
     * Returns the [StyleSet] for the given `state`.
     */
    fun fetchStyleFor(state: ComponentState): StyleSet

    companion object {

        // TODO: is this OK?
        val UNKNOWN = componentStyleSet { }

        /**
         * Returns the empty [ComponentStyleSet] which uses [StyleSet.defaultStyle]
         * for all states.
         */
        val DEFAULT_STYLE = componentStyleSet { }

        /**
         * Returns the empty [ComponentStyleSet] which uses [StyleSet.empty]
         * for all states.
         */
        val EMPTY = componentStyleSet {
            defaultStyle = StyleSet.empty()
        }
    }
}
