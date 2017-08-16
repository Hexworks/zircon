package org.codetome.zircon.api

import org.codetome.zircon.Modifier
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.color.TextColor
import org.codetome.zircon.graphics.style.StyleSet

/**
 * Builds [TextCharacter]s.
 * Defaults:
 * - Default character is a space
 * - Default modifiers is an empty set
 * also
 * @see TextColorFactory to check default colors
 */
class TextCharacterBuilder {
    private var character: Char = ' '
    private var foregroundColor: TextColor = TextColorFactory.DEFAULT_FOREGROUND_COLOR
    private var backgroundColor: TextColor = TextColorFactory.DEFAULT_BACKGROUND_COLOR
    private var modifiers: Set<Modifier> = setOf()

    fun character(character: Char) = also {
        this.character = character
    }

    /**
     * Sets the styles (colors and modifiers) from the given
     * `styleSet`.
     */
    fun styleSet(styleSet: StyleSet) = also {
        backgroundColor = styleSet.getBackgroundColor()
        foregroundColor = styleSet.getForegroundColor()
        modifiers = styleSet.getActiveModifiers().toSet()
    }

    fun foregroundColor(foregroundColor: TextColor) = also {
        this.foregroundColor = foregroundColor
    }

    fun backgroundColor(backgroundColor: TextColor) = also {
        this.backgroundColor = backgroundColor
    }

    fun modifiers(modifiers: Set<Modifier>) = also {
        this.modifiers = modifiers.toSet()
    }

    fun modifier(vararg modifiers: Modifier) = also {
        this.modifiers = modifiers.toSet()
    }

    fun build() = TextCharacter.of(
            character = character,
            foregroundColor = foregroundColor,
            backgroundColor = backgroundColor,
            modifiers = modifiers)

    companion object {

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
        val DEFAULT_CHARACTER = TextCharacter.builder()
                .build()

        /**
         * Shorthand for the default character which is:
         * - a space character
         * - with transparent foreground
         * - and transparent background
         * - and no modifiers.
         */
        @JvmField
        val EMPTY = TextCharacter.builder()
                .backgroundColor(TextColorFactory.TRANSPARENT)
                .foregroundColor(TextColorFactory.TRANSPARENT)
                .character(' ')
                .build()
    }
}