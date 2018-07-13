package org.codetome.zircon.api.interop

import org.codetome.zircon.api.builder.StyleSetBuilder
import org.codetome.zircon.internal.multiplatform.factory.TextColorFactory

object StyleSets {

    /**
     * Creates a new [StyleSetBuilder] for creating [org.codetome.zircon.api.graphics.StyleSet]s.
     */
    @JvmStatic
    fun newBuilder() = StyleSetBuilder()

    /**
     * Shorthand for the default character which is:
     * - a space character
     * - with default foreground
     * - and default background
     * - and no modifiers.
     */
    @JvmField
    val DEFAULT_STYLE = newBuilder().build()

    /**
     * Shorthand for the empty style which has:
     * - transparent foreground
     * - and transparent background
     * - and no modifiers.
     */
    @JvmField
    val EMPTY = newBuilder()
            .backgroundColor(TextColorFactory.transparent())
            .foregroundColor(TextColorFactory.transparent())
            .modifiers(setOf())
            .build()

}
