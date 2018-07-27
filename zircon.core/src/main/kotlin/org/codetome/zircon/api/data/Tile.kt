package org.codetome.zircon.api.data

import org.codetome.zircon.api.behavior.Cacheable
import org.codetome.zircon.api.behavior.DrawSurface
import org.codetome.zircon.api.behavior.Drawable
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.modifier.Border
import org.codetome.zircon.api.modifier.Modifier
import org.codetome.zircon.internal.data.DefaultTile

/**
 * Represents a single tile with additional metadata such as colors and modifiers.
 * **Note that** a [Tile] has to be immutable and cannot be modified after creation.
 *
 * Use the with* methods to of new instances based on this one.
 *
 */
interface Tile : Cacheable, Drawable {

    /**
     * Tells whether this [Tile] is opaque, eg: the background color's alpha is 255.
     */
    fun isOpaque() = getBackgroundColor().getAlpha() == 255

    fun getCharacter(): Char

    fun getForegroundColor(): TextColor

    fun getBackgroundColor(): TextColor

    fun getModifiers(): Set<Modifier>

    fun toStyleSet(): StyleSet

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

    override fun generateCacheKey(): String {
        return "c:${getCharacter()}" +
                "ss:${toStyleSet().generateCacheKey()}" +
                "t:${getTags().sorted().joinToString(separator = "")}"
    }

    override fun drawOnto(surface: DrawSurface, offset: Position) {
        surface.setCharacterAt(offset, this)
    }

    companion object {

        /**
         * Shorthand for the default character which is:
         * - a space character
         * - with default foreground
         * - and default background
         * - and no modifiers.
         */
        fun defaultTile() = DEFAULT_CHARACTER

        /**
         * Shorthand for an empty character which is:
         * - a space character
         * - with transparent foreground
         * - and transparent background
         * - and no modifiers.
         */
        fun empty() = EMPTY

        /**
         * Creates a new [Tile].
         */
        fun create(character: Char,
                   styleSet: StyleSet,
                   tags: Set<String> = setOf()) = DefaultTile(
                character = character,
                styleSet = styleSet,
                tags = tags)

        private val DEFAULT_CHARACTER = DefaultTile(
                character = ' ',
                styleSet = StyleSet.defaultStyle(),
                tags = setOf())

        private val EMPTY = DefaultTile(
                character = ' ',
                styleSet = StyleSet.empty(),
                tags = setOf())

    }

}
