package org.codetome.zircon.api.builder

import org.codetome.zircon.internal.DefaultTextCharacter
import org.codetome.zircon.api.Modifier
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.color.TextColorFactory
import org.codetome.zircon.api.graphics.StyleSet

/**
 * Builds [TextCharacter]s.
 * Defaults:
 * - Default character is a space
 * - Default modifiers is an empty set
 * also
 * @see TextColorFactory to check default colors
 */
data class TextCharacterBuilder(
        private var character: Char = ' ',
        private var foregroundColor: TextColor = TextColorFactory.DEFAULT_FOREGROUND_COLOR,
        private var backgroundColor: TextColor = TextColorFactory.DEFAULT_BACKGROUND_COLOR,
        private var modifiers: Set<Modifier> = setOf(),
        private var tags: Set<String> = setOf()) : Builder<TextCharacter> {

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
        modifiers = styleSet.getActiveModifiers().toSet() // TODO: test defensive copy
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

    fun tag(vararg tags: String) = also {
        this.tags = tags.toSet()
    }

    fun tags(tags: Set<String>) = also {
        this.tags = tags
    }

    override fun build(): TextCharacter = DefaultTextCharacter.of(
            character = character,
            foregroundColor = foregroundColor,
            backgroundColor = backgroundColor,
            modifiers = modifiers,
            tags = tags)

    override fun createCopy() = copy(
            modifiers = modifiers.toSet(),
            tags = tags.toSet()) // TODO: test defensive copy

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
                .backgroundColor(TextColorFactory.TRANSPARENT)
                .foregroundColor(TextColorFactory.TRANSPARENT)
                .character(' ')
                .build()
    }
}