package org.codetome.zircon.api

import org.codetome.zircon.api.behavior.Cacheable
import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.modifier.Border
import org.codetome.zircon.internal.DefaultTextCharacter
import org.codetome.zircon.platform.factory.TextCharacterFactory

/**
 * Represents a single character with additional metadata such as colors and modifiers.
 * **Note that** a [TextCharacter] have to be immutable and cannot be modified after creation.
 *
 * Note that you can only of [TextCharacter]s out of printable characters (No tabs for
 * example)!
 *
 * Use the with* methods to of new instances based on this one.
 *
 * Two [TextCharacter]s are considered `equal` if they have the same:
 * - character,
 * - styleSet and
 * tags!
 */
interface TextCharacter : Cacheable {

    /**
     * Tells whether this [TextCharacter] is opaque, eg: the background color's alpha is 255.
     */
    fun isOpaque() = getBackgroundColor().getAlpha() == 255

    fun getCharacter(): Char

    fun getForegroundColor(): TextColor

    fun getBackgroundColor(): TextColor

    fun getModifiers(): Set<Modifier>

    fun getTags(): Set<String>

    fun isBold(): Boolean

    fun isUnderlined(): Boolean

    fun isCrossedOut(): Boolean

    fun isItalic(): Boolean

    fun isBlinking(): Boolean

    fun hasBorder(): Boolean

    fun fetchBorderData(): Set<Border>

    fun isNotEmpty(): Boolean = this != TextCharacterBuilder.empty()

    /**
     * Returns a copy of this [TextCharacter] with the specified character.
     */
    fun withCharacter(character: Char): TextCharacter

    /**
     * Returns a copy of this [TextCharacter] with the specified foreground color.
     */
    fun withForegroundColor(foregroundColor: TextColor): TextCharacter

    /**
     * Returns a copy of this [TextCharacter] with the specified background color.
     */
    fun withBackgroundColor(backgroundColor: TextColor): TextCharacter

    /**
     * Returns a copy of this [TextCharacter] with the specified style.
     */
    fun withStyle(styleSet: StyleSet): TextCharacter

    /**
     * Returns a copy of this [TextCharacter] with the specified tags.
     */
    fun withTags(tags: Set<String>): TextCharacter

    /**
     * Returns a copy of this [TextCharacter] with the specified tags.
     */
    fun withTags(vararg tags: String): TextCharacter

    /**
     * Returns a copy of this [TextCharacter] with the specified modifiers.
     */
    fun withModifiers(vararg modifiers: Modifier): TextCharacter

    /**
     * Returns a copy of this [TextCharacter] with the specified modifiers.
     */
    fun withModifiers(modifiers: Set<Modifier>): TextCharacter

    /**
     * Returns a copy of this [TextCharacter] with [Modifier] (s) removed.
     * The currently active [Modifier]s will be carried over to the copy, except for the one(s) specified.
     * If the current [TextCharacter] doesn't have the [Modifier] (s) specified, it will return itself.
     */
    fun withoutModifiers(vararg modifiers: Modifier): TextCharacter

    /**
     * Returns a copy of this [TextCharacter] with [Modifier] (s) removed.
     * The currently active [Modifier]s will be carried over to the copy, except for the one(s) specified.
     * If the current [TextCharacter] doesn't have the [Modifier] (s) specified, it will return itself.
     */
    fun withoutModifiers(modifiers: Set<Modifier>): TextCharacter

    companion object {
        /**
         * Creates a new [DefaultTextCharacter].
         */
        fun create(character: Char,
                   styleSet: StyleSet,
                   tags: Set<String> = setOf()) = TextCharacterFactory.create(character, styleSet, tags)
    }

}
