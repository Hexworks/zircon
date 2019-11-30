package org.hexworks.zircon.api

import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.graphics.StyleSet
import kotlin.jvm.JvmStatic

object StyleSets {

    /**
     * Shorthand for the default style which is:
     * - default foreground color (white)
     * - default background color (black)
     * - no modifiers
     */
    @Deprecated("Use StyleSet.defaultStyle instead", replaceWith = ReplaceWith(
            "StyleSet.defaultStyle", "org.hexworks.zircon.api.graphics.StyleSet"))
    @JvmStatic
    fun defaultStyle() = StyleSet.defaultStyle()

    /**
     * Shorthand for the empty style which has:
     * - transparent foreground
     * - and transparent background
     * - and no modifiers.
     */
    @Deprecated("Use StyleSet.empty instead", replaceWith = ReplaceWith(
            "StyleSet.empty", "org.hexworks.zircon.api.graphics.StyleSet"))
    @JvmStatic
    fun empty() = StyleSet.empty()

    /**
     * Creates a new [StyleSetBuilder] for creating [org.hexworks.zircon.api.graphics.StyleSet]s.
     */
    @Deprecated("Use StyleSet.newBuilder instead", replaceWith = ReplaceWith(
            "StyleSet.newBuilder", "org.hexworks.zircon.api.graphics.StyleSet"))
    @JvmStatic
    fun newBuilder() = StyleSetBuilder()

}
