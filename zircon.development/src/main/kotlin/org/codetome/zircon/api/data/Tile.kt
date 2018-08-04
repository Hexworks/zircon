package org.codetome.zircon.api.data

import org.codetome.zircon.api.behavior.Cacheable
import org.codetome.zircon.api.behavior.DrawSurface
import org.codetome.zircon.api.behavior.Drawable
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.modifier.Border
import org.codetome.zircon.api.modifier.Modifier
import org.codetome.zircon.api.modifier.SimpleModifiers.*
import kotlin.reflect.KClass

interface Tile : Drawable, Cacheable {

    fun tileType(): KClass<out Tile>

    fun isOpaque(): Boolean = getForegroundColor().isOpaque().and(
            getBackgroundColor().isOpaque())

    fun getForegroundColor(): TextColor = toStyleSet().getForegroundColor()

    fun getBackgroundColor(): TextColor = toStyleSet().getBackgroundColor()

    fun getModifiers(): Set<Modifier> = toStyleSet().getModifiers()

    fun toStyleSet(): StyleSet

    fun isUnderlined(): Boolean = getModifiers().contains(Underline)

    fun isCrossedOut(): Boolean = getModifiers().contains(CrossedOut)

    fun isBlinking(): Boolean = getModifiers().contains(Blink)

    fun hasBorder(): Boolean = getModifiers().any { it is Border }

    fun fetchBorderData(): Set<Border> = getModifiers()
            .filter { it is Border }
            .map { it as Border }
            .toSet()

    fun isNotEmpty(): Boolean = this != empty()

    override fun drawOnto(surface: DrawSurface, offset: Position) {
        surface.setTileAt(offset, this)
    }

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
        fun defaultTile() = DEFAULT_CHARACTER_TILE

        /**
         * Shorthand for an empty character tile which is:
         * - a space character
         * - with transparent foreground
         * - and transparent background
         * - and no modifiers.
         */
        fun empty() = EMPTY_CHARACTER_TILE

        /**
         * Creates a new [Tile].
         */
        fun create(character: Char,
                   style: StyleSet) = CharacterTile(
                character = character,
                style = style)

        private val DEFAULT_CHARACTER_TILE = CharacterTile(
                character = ' ',
                style = StyleSet.defaultStyle())

        private val EMPTY_CHARACTER_TILE = CharacterTile(
                character = ' ',
                style = StyleSet.empty())

    }
}
