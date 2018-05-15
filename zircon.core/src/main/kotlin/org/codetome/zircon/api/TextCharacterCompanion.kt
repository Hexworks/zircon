package org.codetome.zircon.api

import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.api.color.TextColorFactory

interface TextCharacterCompanion {

    /**
     * Creates a new [TextCharacterBuilder] for creating [TextCharacter]s.
     */
    fun newBuilder() = TextCharacterBuilder()

    /**
     * Shorthand for the default character which is:
     * - a space character
     * - with default foreground
     * - and default background
     * - and no modifiers.
     */
    fun defaultCharacter() = TextCharacterBuilder.newBuilder().build()

    /**
     * Shorthand for an empty character which is:
     * - a space character
     * - with transparent foreground
     * - and transparent background
     * - and no modifiers.
     */
    fun empty() = TextCharacterBuilder.newBuilder()
            .backgroundColor(TextColorFactory.transparent())
            .foregroundColor(TextColorFactory.transparent())
            .character(' ')
            .build()
}
