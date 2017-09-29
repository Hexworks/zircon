package org.codetome.zircon.internal

import org.codetome.zircon.api.Modifier
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.util.TextUtils
import org.codetome.zircon.internal.BuiltInModifiers.*

data class DefaultTextCharacter(
        private val character: Char,
        private val foregroundColor: TextColor,
        private val backgroundColor: TextColor,
        private val modifiers: Set<Modifier>,
        private val tags: Set<String>) : TextCharacter {

    init {
        require(TextUtils.isPrintableCharacter(character)) {
            "Can't create a TextCharacter out create a non-printable character: '${character.toInt()}'"
        }
    }

    override fun getCharacter(): Char = character

    override fun getForegroundColor(): TextColor = foregroundColor

    override fun getBackgroundColor(): TextColor = backgroundColor

    override fun getModifiers(): Set<Modifier> = modifiers

    override fun getTags(): Set<String> = tags

    override fun isBold(): Boolean = modifiers.contains(Bold)

    override fun isUnderlined(): Boolean = modifiers.contains(Underline)

    override fun isCrossedOut(): Boolean = modifiers.contains(CrossedOut)

    override fun isItalic(): Boolean = modifiers.contains(Italic)

    override fun isBlinking(): Boolean = modifiers.contains(Blink)

    override fun hasBorder(): Boolean = modifiers.any { it is Border }

    override fun fetchBorderData(): Set<BuiltInModifiers.Border> = modifiers
            .filter { it is Border }
            .map { it as Border }
            .toSet()

    override fun isNotEmpty(): Boolean = this != TextCharacterBuilder.EMPTY

    override fun withCharacter(character: Char): DefaultTextCharacter {
        if (this.character == character) {
            return this
        }
        return copy(character = character)
    }

    override fun withForegroundColor(foregroundColor: TextColor): DefaultTextCharacter {
        if (this.foregroundColor == foregroundColor) {
            return this
        }
        return copy(foregroundColor = foregroundColor)
    }

    override fun withBackgroundColor(backgroundColor: TextColor): DefaultTextCharacter {
        if (this.backgroundColor == backgroundColor) {
            return this
        }
        return copy(backgroundColor = backgroundColor)
    }

    override fun withModifiers(vararg modifiers: Modifier): DefaultTextCharacter {
        return withModifiers(modifiers.toSet())
    }

    override fun withModifiers(modifiers: Set<Modifier>): DefaultTextCharacter {
        if (this.modifiers == modifiers) {
            return this
        }
        val newSet = this.modifiers.plus(modifiers)
        return copy(modifiers = newSet)
    }

    override fun withoutModifiers(vararg modifiers: Modifier): DefaultTextCharacter {
        return withoutModifiers(modifiers.toSet())
    }

    override fun withoutModifiers(modifiers: Set<Modifier>): DefaultTextCharacter {
        if (this.modifiers.none { modifiers.contains(it) }) {
            return this
        }
        val newSet = this.modifiers.toMutableSet()
        newSet.removeAll(modifiers)
        return copy(modifiers = newSet.toSet()) // TODO: test defensive copying!
    }

    override fun withStyle(styleSet: StyleSet): TextCharacter = copy(
            foregroundColor = styleSet.getForegroundColor(),
            backgroundColor = styleSet.getBackgroundColor(),
            modifiers = styleSet.getActiveModifiers().toSet())

    companion object {

        /**
         * Creates a new [DefaultTextCharacter]. This method is necessary
         * because a defensive copy create `modifiers` needs to be forced.
         */
        @JvmStatic
        @JvmOverloads
        fun of(character: Char,
               foregroundColor: TextColor,
               backgroundColor: TextColor,
               modifiers: Set<Modifier> = setOf(),
               tags: Set<String> = setOf()) = DefaultTextCharacter(
                character = character,
                foregroundColor = foregroundColor,
                backgroundColor = backgroundColor,
                modifiers = modifiers.toSet(),
                tags = tags)
    }

}