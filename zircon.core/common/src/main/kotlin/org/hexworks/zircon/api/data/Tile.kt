package org.hexworks.zircon.api.data

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.behavior.Cacheable
import org.hexworks.zircon.api.behavior.Drawable
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.tile.CharacterTile
import org.hexworks.zircon.api.data.tile.GraphicalTile
import org.hexworks.zircon.api.data.tile.ImageTile
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.Border
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.internal.resource.TileType
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.data.DefaultCharacterTile
import org.hexworks.zircon.internal.data.DefaultGraphicalTile
import org.hexworks.zircon.internal.data.DefaultImageTile

/**
 * A [Tile] is the basic building block which can be drawn
 * on a screen. It is a rectangular graphic, or character
 * which can be composed to more complex objects.
 */
interface Tile : Drawable, Cacheable, StyleSet {

    val tileType: TileType
    val styleSet: StyleSet

    override fun createCopy(): Tile

    override fun withForegroundColor(foregroundColor: TileColor): Tile

    override fun withBackgroundColor(backgroundColor: TileColor): Tile

    override fun withModifiers(modifiers: Set<Modifier>): Tile

    override fun withModifiers(vararg modifiers: Modifier): Tile

    override fun withAddedModifiers(modifiers: Set<Modifier>): Tile

    override fun withAddedModifiers(vararg modifiers: Modifier): Tile

    override fun withRemovedModifiers(modifiers: Set<Modifier>): Tile

    override fun withRemovedModifiers(vararg modifiers: Modifier): Tile

    override fun withNoModifiers(): Tile

    /**
     * Returns a copy of this [Tile] with the specified style.
     */
    fun withStyle(style: StyleSet): Tile

    /**
     * Returns this [Tile] as a [CharacterTile] if possible.
     */
    fun asCharacterTile(): Maybe<CharacterTile>

    /**
     * Returns this [Tile] as an [ImageTile] if possible.
     */
    fun asImageTile(): Maybe<ImageTile>

    /**
     * Returns this [Tile] as a [GraphicalTile] if possible.
     */
    fun asGraphicTile(): Maybe<GraphicalTile>

    fun isOpaque(): Boolean

    fun isUnderlined(): Boolean

    fun isCrossedOut(): Boolean

    fun isBlinking(): Boolean

    fun isVerticalFlipped(): Boolean

    fun isHorizontalFlipped(): Boolean

    fun hasBorder(): Boolean

    fun fetchBorderData(): Set<Border>

    /**
     * Tells whether this [Tile] **is** an empty [Tile]
     * (it is the [Tile.empty] instance).
     */
    fun isEmpty(): Boolean

    /**
     * Tells whether this [Tile] **is not** an empty [Tile]
     * (it is not the [Tile.empty] instance).
     */
    fun isNotEmpty(): Boolean

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
         * Creates a new [CharacterTile].
         */
        fun createCharacterTile(character: Char, style: StyleSet): CharacterTile {
            return DefaultCharacterTile(
                    character = character,
                    styleSet = style)
        }

        /**
         * Creates a new [ImageTile].
         */
        fun createImageTile(name: String, tileset: TilesetResource): ImageTile {
            return DefaultImageTile(
                    tileset = tileset,
                    name = name)
        }

        /**
         * Creates a new [GraphicalTile].
         */
        fun createGraphicTile(name: String,
                              tags: Set<String>,
                              tileset: TilesetResource = RuntimeConfig.config.defaultGraphicTileset): GraphicalTile {
            return DefaultGraphicalTile(
                    name = name,
                    tags = tags,
                    tileset = tileset)
        }

        private val DEFAULT_CHARACTER_TILE: CharacterTile = DefaultCharacterTile(
                character = ' ',
                styleSet = StyleSet.defaultStyle())

        private val EMPTY_CHARACTER_TILE: CharacterTile = DefaultCharacterTile(
                character = ' ',
                styleSet = StyleSet.empty())

    }
}
