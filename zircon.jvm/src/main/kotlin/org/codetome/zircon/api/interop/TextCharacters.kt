package org.codetome.zircon.api.interop

import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.api.color.TextColorFactory

object TextCharacters {

    /**
     * Creates a new [TextCharacterBuilder] for creating [TextCharacter]s.
     */
    @JvmStatic
    fun newBuilder() = TextCharacterBuilder()

    /**
     * Shorthand for the default character which is:
     * - a space character
     * - with default foreground
     * - and default background
     * - and no modifiers.
     */
    @JvmField
    val DEFAULT_CHARACTER = TextCharacterBuilder.newBuilder().build()

    /**
     * Shorthand for an empty character which is:
     * - a space character
     * - with transparent foreground
     * - and transparent background
     * - and no modifiers.
     */
    @JvmField
    val EMPTY = TextCharacterBuilder.newBuilder()
            .backgroundColor(TextColorFactory.transparent())
            .foregroundColor(TextColorFactory.transparent())
            .character(' ')
            .build()
}
