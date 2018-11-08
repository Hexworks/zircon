package org.hexworks.zircon.api

import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.component.ComponentStyleSet

object ComponentStyleSets {

    /**
     * Creates a new [ComponentStyleSetBuilder].
     */
    @JvmStatic
    fun newBuilder() = ComponentStyleSetBuilder()

    /**
     * Returns the default [ComponentStyleSet] which uses the
     * default [org.hexworks.zircon.api.graphics.StyleSet].
     */
    @JvmStatic
    fun defaultStyleSet() = ComponentStyleSet.defaultStyleSet()
}
