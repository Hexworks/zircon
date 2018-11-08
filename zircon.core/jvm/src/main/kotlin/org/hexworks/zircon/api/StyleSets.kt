package org.hexworks.zircon.api

import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.graphics.StyleSet

object StyleSets {

    /**
     * Shorthand for the default style which is:
     * - default foreground color (white)
     * - default background color (black)
     * - no modifiers
     */
    @JvmStatic
    fun defaultStyle() = StyleSet.defaultStyle()

    /**
     * Shorthand for the empty style which has:
     * - transparent foreground
     * - and transparent background
     * - and no modifiers.
     */
    @JvmStatic
    fun empty() = StyleSet.empty()

    /**
     * Creates a new [StyleSetBuilder] for creating [org.hexworks.zircon.api.graphics.StyleSet]s.
     */
    @JvmStatic
    fun newBuilder() = StyleSetBuilder()

}
