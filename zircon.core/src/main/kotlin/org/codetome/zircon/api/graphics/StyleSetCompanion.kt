package org.codetome.zircon.api.graphics

import org.codetome.zircon.api.builder.StyleSetBuilder
import org.codetome.zircon.api.color.TextColorFactory

interface StyleSetCompanion {

    /**
     * Creates a new [StyleSetBuilder] for creating [org.codetome.zircon.api.graphics.StyleSet]s.
     */
    fun newBuilder() = StyleSetBuilder()

    /**
     * Shorthand for the default character which is:
     * - a space character
     * - with default foreground
     * - and default background
     * - and no modifiers.
     */
    fun defaultStyle() = newBuilder().build()

    /**
     * Shorthand for the empty style which has:
     * - transparent foreground
     * - and transparent background
     * - and no modifiers.
     */
    fun empty() = newBuilder()
            .backgroundColor(TextColorFactory.transparent())
            .foregroundColor(TextColorFactory.transparent())
            .modifiers(setOf())
            .build()
}
