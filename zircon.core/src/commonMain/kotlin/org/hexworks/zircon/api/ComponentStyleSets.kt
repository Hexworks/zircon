package org.hexworks.zircon.api

import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.component.ComponentStyleSet

/**
 * This *facade* contains factory functions for creating builders for [ComponentStyleSet]s.
 */
object ComponentStyleSets {

    /**
     * Creates a new [ComponentStyleSetBuilder].
     */
    fun newBuilder() = ComponentStyleSetBuilder.newBuilder()

    /**
     * Returns the default [ComponentStyleSet] which uses the
     * default [org.hexworks.zircon.api.graphics.StyleSet].
     */
    fun defaultStyleSet() = ComponentStyleSet.defaultStyleSet()

    /**
     * Returns the empty [ComponentStyleSet] which uses the
     * empty [org.hexworks.zircon.api.graphics.StyleSet].
     */
    fun empty() = ComponentStyleSet.empty()
}
