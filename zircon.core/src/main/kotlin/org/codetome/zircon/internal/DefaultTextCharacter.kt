package org.codetome.zircon.internal

import org.codetome.zircon.api.Modifier
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.modifier.Border
import org.codetome.zircon.api.modifier.SimpleModifiers.*

data class DefaultTextCharacter(
        private val character: Char,
        private val styleSet: StyleSet,
        private val tags: Set<String>) : TextCharacter {

    override fun getCharacter(): Char = character

    override fun getForegroundColor(): TextColor = styleSet.getForegroundColor()

    override fun getBackgroundColor(): TextColor = styleSet.getBackgroundColor()

    override fun getModifiers(): Set<Modifier> = styleSet.getModifiers()

    override fun getTags(): Set<String> = tags

    override fun isBold(): Boolean = getModifiers().contains(Bold)

    override fun isUnderlined(): Boolean = getModifiers().contains(Underline)

    override fun isCrossedOut(): Boolean = getModifiers().contains(CrossedOut)

    override fun isItalic(): Boolean = getModifiers().contains(Italic)

    override fun isBlinking(): Boolean = getModifiers().contains(Blink)

    override fun hasBorder(): Boolean = getModifiers().any { it is Border }

    override fun fetchBorderData(): Set<Border> = getModifiers()
            .filter { it is Border }
            .map { it as Border }
            .toSet()

    override fun isNotEmpty(): Boolean = this != TextCharacter.empty()

    override fun withCharacter(character: Char): DefaultTextCharacter {
        if (this.character == character) {
            return this
        }
        return copy(character = character)
    }

    override fun withForegroundColor(foregroundColor: TextColor): DefaultTextCharacter {
        if (this.styleSet.getForegroundColor() == foregroundColor) {
            return this
        }
        return copy(styleSet = styleSet.withForegroundColor(foregroundColor))
    }

    override fun withBackgroundColor(backgroundColor: TextColor): DefaultTextCharacter {
        if (this.styleSet.getBackgroundColor() == backgroundColor) {
            return this
        }
        return copy(styleSet = styleSet.withBackgroundColor(backgroundColor))
    }

    override fun withModifiers(vararg modifiers: Modifier): DefaultTextCharacter {
        return withModifiers(modifiers.toSet())
    }

    override fun withModifiers(modifiers: Set<Modifier>): DefaultTextCharacter {
        if (this.styleSet.getModifiers() == modifiers) {
            return this
        }
        return copy(styleSet = styleSet.withModifiers(modifiers))
    }

    override fun withoutModifiers(vararg modifiers: Modifier): DefaultTextCharacter {
        return withoutModifiers(modifiers.toSet())
    }

    override fun withoutModifiers(modifiers: Set<Modifier>): DefaultTextCharacter {
        if (this.styleSet.getModifiers().none { modifiers.contains(it) }) {
            return this
        }
        return copy(styleSet = styleSet.withRemovedModifiers(modifiers))
    }

    override fun withStyle(styleSet: StyleSet) = copy(styleSet = styleSet)

    override fun withTags(vararg tags: String) = withTags(tags.toSet())

    override fun withTags(tags: Set<String>) = copy(tags = tags)

    override fun generateCacheKey() = TextCharacter.generateCacheKey(
            character = character,
            styleSet = styleSet,
            tags = tags)

}
