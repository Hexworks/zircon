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
public interface ComponentStyleSet {

    /**
     * Returns the [StyleSet] for the given `state`.
     */
    public fun fetchStyleFor(state: ComponentState): StyleSet

    public companion object {

        // TODO: is this OK?
        public val UNKNOWN: ComponentStyleSet = componentStyleSet { }

        /**
         * Returns the empty [ComponentStyleSet] which uses [StyleSet.defaultStyle]
         * for all states.
         */
        public val DEFAULT_STYLE: ComponentStyleSet = componentStyleSet { }

        /**
         * Returns the empty [ComponentStyleSet] which uses [StyleSet.empty]
         * for all states.
         */
        public val EMPTY: ComponentStyleSet = componentStyleSet {
            defaultStyle = StyleSet.empty()
        }
    }
}
