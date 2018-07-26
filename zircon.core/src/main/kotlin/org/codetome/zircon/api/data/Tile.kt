package org.codetome.zircon.api.data

import org.codetome.zircon.api.modifier.Modifier
import org.codetome.zircon.api.behavior.Cacheable
import org.codetome.zircon.api.builder.data.TileBuilder
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.modifier.Border
import org.codetome.zircon.internal.factory.TextCharacterFactory

/**
 * Represents a single tile with additional metadata such as colors and modifiers.
 * **Note that** a [Tile] has to be immutable and cannot be modified after creation.
 *
 * Use the with* methods to of new instances based on this one.
 *
 */
interface Tile : Cacheable {

    /**
     * Tells whether this [Tile] is opaque, eg: the background color's alpha is 255.
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

    fun isNotEmpty(): Boolean = this != empty()

    /**
     * Returns a copy of this [Tile] with the specified character.
     */
    fun withCharacter(character: Char): Tile

    /**
     * Returns a copy of this [Tile] with the specified foreground color.
     */
    fun withForegroundColor(foregroundColor: TextColor): Tile

    /**
     * Returns a copy of this [Tile] with the specified background color.
     */
    fun withBackgroundColor(backgroundColor: TextColor): Tile

    /**
     * Returns a copy of this [Tile] with the specified style.
     */
    fun withStyle(styleSet: StyleSet): Tile

    /**
     * Returns a copy of this [Tile] with the specified tags.
     */
    fun withTags(tags: Set<String>): Tile

    /**
     * Returns a copy of this [Tile] with the specified tags.
     */
    fun withTags(vararg tags: String): Tile

    /**
     * Returns a copy of this [Tile] with the specified modifiers.
     */
    fun withModifiers(vararg modifiers: Modifier): Tile

    /**
     * Returns a copy of this [Tile] with the specified modifiers.
     */
    fun withModifiers(modifiers: Set<Modifier>): Tile

    /**
     * Returns a copy of this [Tile] with [Modifier] (s) removed.
     * The currently active [Modifier]s will be carried over to the copy, except for the one(s) specified.
     * If the current [Tile] doesn't have the [Modifier] (s) specified, it will return itself.
     */
    fun withoutModifiers(vararg modifiers: Modifier): Tile

    /**
     * Returns a copy of this [Tile] with [Modifier] (s) removed.
     * The currently active [Modifier]s will be carried over to the copy, except for the one(s) specified.
     * If the current [Tile] doesn't have the [Modifier] (s) specified, it will return itself.
     */
    fun withoutModifiers(modifiers: Set<Modifier>): Tile

    companion object {

        /**
         * Shorthand for the default character which is:
         * - a space character
         * - with default foreground
         * - and default background
         * - and no modifiers.
         */
        fun defaultCharacter() = TileBuilder.newBuilder().build()

        /**
         * Shorthand for an empty character which is:
         * - a space character
         * - with transparent foreground
         * - and transparent background
         * - and no modifiers.
         */
        fun empty() = TileBuilder.newBuilder()
                .backgroundColor(TextColor.transparent())
                .foregroundColor(TextColor.transparent())
                .character(' ')
                .build()

        fun create(character: Char,
                   styleSet: StyleSet,
                   tags: Set<String> = setOf()) = TextCharacterFactory.create(character, styleSet, tags)

        internal fun generateCacheKey(character: Char, styleSet: StyleSet, tags: Set<String>): String =
                StringBuilder().apply {
                    append(character)
                    append(styleSet.generateCacheKey())
                    append(tags.sorted().joinToString(separator = ""))
                }.toString()
    }

}
