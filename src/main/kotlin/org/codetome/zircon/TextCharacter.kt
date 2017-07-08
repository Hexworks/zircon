package org.codetome.zircon

import org.codetome.zircon.Modifier.*

/**
 * Represents a single character with additional metadata such as colors and modifiers. This class is immutable and
 * cannot be modified after creation.
 */
data class TextCharacter(
        private val character: Char = ' ',
        private val foregroundColor: TextColor = TextColor.ANSI.WHITE,
        private val backgroundColor: TextColor = TextColor.ANSI.BLACK,
        private val modifiersToUse: Set<Modifier> = setOf()) {

    private val modifiers: Set<Modifier> = modifiersToUse.toSet()

    fun getCharacter() = character

    fun getForegroundColor() = foregroundColor

    fun getBackgroundColor() = backgroundColor

    fun getModifiers() = modifiers

    fun isBold() = modifiers.contains(BOLD)

    fun isUnderlined() = modifiers.contains(UNDERLINE)

    fun isInverse() = modifiers.contains(INVERSE)

    fun isCrossedOut() = modifiers.contains(CROSSED_OUT)

    fun isItalic() = modifiers.contains(ITALIC)

    fun isBlinking() = modifiers.contains(BLINK)

    /**
     * Returns a new TextCharacter with the same colors and modifiers but a different underlying character.
     */
    fun withCharacter(character: Char): TextCharacter {
        if (this.character == character) {
            return this
        }
        return TextCharacter(character, foregroundColor, backgroundColor, modifiers)
    }

    fun withForegroundColor(foregroundColor: TextColor): TextCharacter {
        if (this.foregroundColor === foregroundColor || this.foregroundColor == foregroundColor) {
            return this
        }
        return TextCharacter(character, foregroundColor, backgroundColor, modifiers)
    }

    /**
     * Returns a copy of this [TextCharacter] with a specified background color
     */
    fun withBackgroundColor(backgroundColor: TextColor): TextCharacter {
        if (this.backgroundColor === backgroundColor || this.backgroundColor == backgroundColor) {
            return this
        }
        return TextCharacter(character, foregroundColor, backgroundColor, modifiers)
    }

    /**
     * Returns a copy of this [TextCharacter] with specified list of [Modifier]s. None of the currently active [Modifier]s
     * will be carried over to the copy, only those in the passed in value.
     */
    fun withModifiers(modifiers: Set<Modifier>): TextCharacter {
        if (this.modifiers == modifiers) {
            return this
        }
        return TextCharacter(character, foregroundColor, backgroundColor, modifiers)
    }

    /**
     * Returns a copy of this [TextCharacter] with an additional [Modifier]. All of the currently active [Modifier]s
     * will be carried over to the copy, in addition to the one specified.
     */
    fun withModifier(modifier: Modifier): TextCharacter {
        if (modifiers.contains(modifier)) {
            return this
        }
        val newSet = this.modifiers.plus(modifier)
        return TextCharacter(character, foregroundColor, backgroundColor, newSet)
    }

    /**
     * Returns a copy of this [TextCharacter] with a [Modifier] removed. All of the currently active [Modifier]s
     * will be carried over to the copy, except for the one specified. If the current [TextCharacter] doesn't have the
     * [Modifier] specified, it will return itself.
     */
    fun withoutModifier(modifier: Modifier): TextCharacter {
        if (!modifiers.contains(modifier)) {
            return this
        }
        val newSet = this.modifiers.minus(modifier)
        return TextCharacter(character, foregroundColor, backgroundColor, newSet)
    }

    companion object {
        val DEFAULT_CHARACTER = TextCharacter(
                character = ' ',
                foregroundColor = TextColor.ANSI.WHITE,
                backgroundColor = TextColor.ANSI.BLACK)

    }
}