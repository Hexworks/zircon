package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.builder.component.componentStyleSet
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.graphics.StyleSet

/**
 * This interface represents a collection of [StyleSet]s which
 * will be used when a [Component]'s [ComponentState] changes.
 * @see ComponentState
 */
interface ComponentStyleSet {

    val isUnknown: Boolean
        get() = this === UNKNOWN

    val isNotUnknown: Boolean
        get() = isUnknown.not()

    /**
     * Returns the [StyleSet] for the given `state`.
     */
    fun fetchStyleFor(state: ComponentState): StyleSet

    companion object {

        // TODO: is this OK?
        private val UNKNOWN = componentStyleSet { }

        /**
         * Returns the empty [ComponentStyleSet] which uses [StyleSet.defaultStyle]
         * for all states.
         */
        fun defaultStyleSet() = componentStyleSet { }

        /**
         * Returns the empty [ComponentStyleSet] which uses [StyleSet.empty]
         * for all states.
         */
        fun empty() = componentStyleSet {
            defaultStyle = StyleSet.empty()
        }

        fun unknown() = UNKNOWN

    }
}
