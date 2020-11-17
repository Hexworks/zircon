package org.hexworks.zircon.api.data

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.behavior.Cacheable
import org.hexworks.zircon.api.builder.data.BlockBuilder
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.Border
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.data.DefaultCharacterTile
import org.hexworks.zircon.internal.data.DefaultGraphicalTile
import org.hexworks.zircon.internal.data.DefaultImageTile
import org.hexworks.zircon.internal.resource.TileType
import org.hexworks.zircon.api.graphics.DrawSurface
import kotlin.jvm.JvmStatic

/**
 * A [Tile] is the basic building block that can be drawn on the screen.
 * It is a rectangular graphic, or character that can also be drawn on
 * [DrawSurface]s using [DrawSurface.draw].
 */
@Suppress("JVM_STATIC_IN_INTERFACE_1_6")
interface Tile : Cacheable, StyleSet {

    /**
     * The type of this [Tile]:
     * - [TileType.CHARACTER_TILE]
     * - [TileType.GRAPHIC_TILE]
     * - [TileType.IMAGE_TILE]
     * @see TileType
     */
    val tileType: TileType

    /**
     * The style information of this [Tile]:
     * - [StyleSet.foregroundColor]
     * - [StyleSet.backgroundColor]
     * - [StyleSet.modifiers]
     * @see StyleSet
     */
    val styleSet: StyleSet

    /**
     * An opaque tile's transparency is zero (you can't see through it).
     */
    val isOpaque: Boolean

    val isUnderlined: Boolean

    val isCrossedOut: Boolean

    val isBlinking: Boolean

    val isVerticalFlipped: Boolean

    val isHorizontalFlipped: Boolean

    val hasBorder: Boolean

    /**
     * Tells whether this [Tile] **is** an empty [Tile]
     * (it is the [Tile.empty] instance).
     */
    val isEmpty: Boolean

    /**
     * Tells whether this [Tile] **is not** an empty [Tile]
     * (it is not the [Tile.empty] instance).
     */
    val isNotEmpty: Boolean

    fun fetchBorderData(): Set<Border>

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

    /**
     * Creates a new [TileBuilder] preconfigured with the contents of
     * this [Tile].
     */
    fun toBuilder(): TileBuilder

    companion object {

        /**
         * Shorthand for the default character which is:
         * - a space character
         * - with default foreground
         * - and default background
         * - and no modifiers.
         */
        @JvmStatic
        fun defaultTile(): CharacterTile = DEFAULT_CHARACTER_TILE

        /**
         * Shorthand for an empty character tile which is:
         * - a space character
         * - with transparent foreground
         * - and transparent background
         * - and no modifiers.
         */
        @JvmStatic
        fun empty(): CharacterTile = EMPTY_CHARACTER_TILE

        /**
         * Creates a new [TileBuilder] for creating [Tile]s.
         */
        @JvmStatic
        fun newBuilder() = TileBuilder()

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
                              tileset: TilesetResource = RuntimeConfig.config.defaultGraphicalTileset): GraphicalTile {
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
