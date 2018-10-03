package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.behavior.Cacheable
import org.hexworks.zircon.api.behavior.DrawSurface
import org.hexworks.zircon.api.behavior.Drawable
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.Border
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.api.modifier.SimpleModifiers.*
import org.hexworks.zircon.api.resource.TileType
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.data.DefaultCharacterTile
import org.hexworks.zircon.internal.data.DefaultImageTile

interface Tile : Drawable, Cacheable {

    /**
     * The tile type of this [Tile].
     */
    val tileType: TileType

    /**
     * The style of this [Tile].
     */
    val styleSet: StyleSet

    /**
     * Returns this [Tile] as a [CharacterTile] if possible.
     */
    fun asCharacterTile() = Maybe.ofNullable(this as? CharacterTile)

    /**
     * Returns this [Tile] as an [ImageTile] if possible.
     */
    fun asImageTile() = Maybe.ofNullable(this as? ImageTile)

    /**
     * Returns this [Tile] as a [GraphicTile] if possible.
     */
    fun asGraphicTile() = Maybe.ofNullable(this as? GraphicTile)

    fun isOpaque(): Boolean = foregroundColor.isOpaque().and(
            backgroundColor.isOpaque())

    val foregroundColor: TileColor
        get() = styleSet.foregroundColor()

    val backgroundColor: TileColor
        get() = styleSet.backgroundColor()

    val modifiers: Set<Modifier>
        get() = styleSet.modifiers()

    fun isUnderlined(): Boolean = modifiers.contains(Underline)

    fun isCrossedOut(): Boolean = modifiers.contains(CrossedOut)

    fun isBlinking(): Boolean = modifiers.contains(Blink)

    fun isVerticalFlipped(): Boolean = modifiers.contains(VerticalFlip)

    fun isHorizontalFlipped(): Boolean = modifiers.contains(HorizontalFlip)

    fun hasBorder(): Boolean = modifiers.any { it is Border }

    fun fetchBorderData(): Set<Border> = modifiers
            .asSequence()
            .filter { it is Border }
            .map { it as Border }
            .toSet()

    fun isNotEmpty(): Boolean = this !== empty()

    override fun drawOnto(surface: DrawSurface, position: Position) {
        surface.setTileAt(position, this)
    }

    /**
     * Returns a copy of this [Tile] with the specified foreground color.
     */
    fun withForegroundColor(foregroundColor: TileColor): Tile

    /**
     * Returns a copy of this [Tile] with the specified background color.
     */
    fun withBackgroundColor(backgroundColor: TileColor): Tile

    /**
     * Returns a copy of this [Tile] with the specified style.
     */
    fun withStyle(style: StyleSet): Tile

    /**
     * Returns a copy of this [Tile] with the given `modifier` added to the current modifiers
     */
    fun withAddedModifier(modifier: Modifier): Tile = withModifiers(modifiers + modifier)

    /**
     * Returns a copy of this [Tile] with the specified modifiers.
     */
    fun withModifiers(vararg modifiers: Modifier): Tile = withModifiers(modifiers.toSet())

    /**
     * Returns a copy of this [Tile] with the specified modifiers.
     */
    fun withModifiers(modifiers: Set<Modifier>): Tile

    /**
     * Returns a copy of this [Tile] with [Modifier] (s) removed.
     * The currently active [Modifier]s will be carried over to the copy, except for the one(s) specified.
     * If the current [Tile] doesn't have the [Modifier] (s) specified, it will returnThis itself.
     */
    fun withoutModifiers(vararg modifiers: Modifier): Tile = withoutModifiers(modifiers.toSet())

    /**
     * Returns a copy of this [Tile] with [Modifier] (s) removed.
     * The currently active [Modifier]s will be carried over to the copy, except for the one(s) specified.
     * If the current [Tile] doesn't have the [Modifier] (s) specified, it will returnThis itself.
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
        fun defaultTile(): CharacterTile = DEFAULT_CHARACTER_TILE

        /**
         * Shorthand for an empty character tile which is:
         * - a space character
         * - with transparent foreground
         * - and transparent background
         * - and no modifiers.
         */
        fun empty(): CharacterTile = EMPTY_CHARACTER_TILE

        /**
         * Creates a new [Tile].
         */
        fun createCharacterTile(character: Char, style: StyleSet): CharacterTile {
            return DefaultCharacterTile(
                    character = character,
                    styleSet = style)
        }

        fun createImageTile(name: String, tileset: TilesetResource, style: StyleSet): ImageTile {
            return DefaultImageTile(
                    tileset = tileset,
                    name = name,
                    style = style)
        }

        private val DEFAULT_CHARACTER_TILE: CharacterTile = DefaultCharacterTile(
                character = ' ',
                styleSet = StyleSet.defaultStyle())

        private val EMPTY_CHARACTER_TILE: CharacterTile = DefaultCharacterTile(
                character = ' ',
                styleSet = StyleSet.empty())

    }
}
