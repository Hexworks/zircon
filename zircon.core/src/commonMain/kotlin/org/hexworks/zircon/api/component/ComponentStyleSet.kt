package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.graphics.StyleSet
import kotlin.jvm.JvmStatic

/**
 * This interface represents a collection of [StyleSet]s which
 * will be used when a [Component]'s [ComponentState] changes.
 * @see ComponentState
 */
@Suppress("JVM_STATIC_IN_INTERFACE_1_6")
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

        private val UNKNOWN = newBuilder().build()

        /**
         * Creates a new [ComponentStyleSetBuilder] for creating styles.
         */
        @JvmStatic
        fun newBuilder(): ComponentStyleSetBuilder = ComponentStyleSetBuilder.newBuilder()

        /**
         * Returns the empty [ComponentStyleSet] which uses [StyleSet.defaultStyle]
         * for all states.
         */
        @JvmStatic
        fun defaultStyleSet() = ComponentStyleSetBuilder.newBuilder().build()

        /**
         * Returns the empty [ComponentStyleSet] which uses [StyleSet.empty]
         * for all states.
         */
        @JvmStatic
        fun empty() = ComponentStyleSetBuilder.newBuilder()
            .withDefaultStyle(StyleSet.empty())
            .build()

        @JvmStatic
        fun unknown() = UNKNOWN

    }
}
