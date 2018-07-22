package org.codetome.zircon.api.interop

import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.builder.graphics.StyleSetBuilder

object StyleSets {

    /**
     * Shorthand for the default character which is:
     * - a space character
     * - with default foreground
     * - and default background
     * - and no modifiers.
     */
    @JvmStatic
    fun defaultStyle() = newBuilder().build()

    /**
     * Shorthand for the empty style which has:
     * - transparent foreground
     * - and transparent background
     * - and no modifiers.
     */
    @JvmStatic
    fun empty() = newBuilder()
            .backgroundColor(TextColor.transparent())
            .foregroundColor(TextColor.transparent())
            .modifiers(setOf())
            .build()

    /**
     * Creates a new [StyleSetBuilder] for creating [org.codetome.zircon.api.graphics.StyleSet]s.
     */
    @JvmStatic
    fun newBuilder() = StyleSetBuilder()

}
