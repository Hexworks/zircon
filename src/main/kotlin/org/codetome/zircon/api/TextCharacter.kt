package org.codetome.zircon.api

import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.internal.BuiltInModifiers.Border

/**
 * Represents a single character with additional metadata such as colors and modifiers.
 * This class is immutable and cannot be modified after creation.
 *
 * Note that you can only of [TextCharacter]s out of printable characters (No tabs for
 * example)!
 *
 * Use the with* methods to of new instances based on this one.
 */
interface TextCharacter {

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

    fun isNotEmpty(): Boolean = this != TextCharacterBuilder.EMPTY

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

}