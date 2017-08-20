package org.codetome.zircon

import org.codetome.zircon.Modifier.*
import org.codetome.zircon.api.TextCharacterBuilder
import org.codetome.zircon.color.TextColor
import org.codetome.zircon.graphics.style.StyleSet

/**
 * Represents a single character with additional metadata such as colors and modifiers. This class is immutable and
 * cannot be modified after creation.
 */
@Suppress("DataClassPrivateConstructor")
data class TextCharacter(
        private val character: Char,
        private val foregroundColor: TextColor,
        private val backgroundColor: TextColor,
        private val modifiers: Set<Modifier>) {

    fun getCharacter() = character

    fun getForegroundColor() = foregroundColor

    fun getBackgroundColor() = backgroundColor

    fun getModifiers() = modifiers

    fun isBold() = modifiers.contains(BOLD)

    fun isUnderlined() = modifiers.contains(UNDERLINE)

    fun isCrossedOut() = modifiers.contains(CROSSED_OUT)

    fun isItalic() = modifiers.contains(ITALIC)

    fun isBlinking() = modifiers.contains(BLINK)

    fun isNotEmpty() = this != TextCharacterBuilder.EMPTY

    /**
     * Returns a new TextCharacter with the same colors and modifiers but a different underlying character.
     */
    fun withCharacter(character: Char): TextCharacter {
        if (this.character == character) {
            return this
        }
        return copy(character = character)
    }

    fun withForegroundColor(foregroundColor: TextColor): TextCharacter {
        if (this.foregroundColor == foregroundColor) {
            return this
        }
        return copy(foregroundColor = foregroundColor)
    }

    /**
     * Returns a copy of this [TextCharacter] with a specified background color
     */
    fun withBackgroundColor(backgroundColor: TextColor): TextCharacter {
        if (this.backgroundColor == backgroundColor) {
            return this
        }
        return copy(backgroundColor = backgroundColor)
    }

    /**
     * Returns a copy of this [TextCharacter] with specified list of [Modifier]s. None of the currently active [Modifier]s
     * will be carried over to the copy, only those in the passed in value.
     */
    fun withModifiers(modifiers: Set<Modifier>): TextCharacter {
        if (this.modifiers == modifiers) {
            return this
        }
        return copy(modifiers = modifiers.toSet())
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
        return copy(modifiers = newSet)
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
        return copy(modifiers = newSet)
    }

    fun withStyle(styleSet: StyleSet) = TextCharacterBuilder.newBuilder()
            .styleSet(styleSet)
            .character(getCharacter())
            .build()

    companion object {

        /**
         * Creates a new [TextCharacterBuilder] for creating [TextCharacter]s.
         */
        @JvmStatic
        fun builder() = TextCharacterBuilder()

        /**
         * Creates a new [TextCharacter]. This method is necessary
         * because a defensive copy of `modifiers` needs to be forced.
         */
        @JvmStatic
        fun of(character: Char,
               foregroundColor: TextColor,
               backgroundColor: TextColor,
               modifiers: Set<Modifier>) = TextCharacter(
                character = character,
                foregroundColor = foregroundColor,
                backgroundColor = backgroundColor,
                modifiers = modifiers.toSet())
    }

}