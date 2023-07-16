package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.behavior.Cacheable
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.graphics.DrawSurface
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.Border
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.internal.data.DefaultCharacterTile
import org.hexworks.zircon.internal.resource.TileType

/**
 * A [Tile] is the basic building block that can be drawn on the screen.
 * It is a rectangular graphic, or character that can also be drawn on
 * [DrawSurface]s using [DrawSurface.draw].
 */
interface Tile : Cacheable, StyleSet {

    /**
     * The type of this [Tile]:
     * - [TileType.CHARACTER_TILE]
     * - [TileType.GRAPHICAL_TILE]
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
    fun asCharacterTileOrNull(): CharacterTile?

    /**
     * Returns this [Tile] as a [CharacterTile] if possible or the
     * result of calling [orElse] with `this` if it is not.
     */
    fun asCharacterTileOrElse(orElse: (Tile) -> CharacterTile): CharacterTile {
        return asCharacterTileOrNull() ?: orElse(this)
    }

    /**
     * Returns this [Tile] as an [ImageTile] if possible.
     */
    fun asImageTileOrNull(): ImageTile?

    /**
     * Returns this [Tile] as a [ImageTile] if possible or the
     * result of calling [orElse] with `this` if it is not.
     */
    fun asImageTileOrElse(orElse: (Tile) -> ImageTile): ImageTile {
        return asImageTileOrNull() ?: orElse(this)
    }

    /**
     * Returns this [Tile] as a [GraphicalTile] if possible.
     */
    fun asGraphicalTileOrNull(): GraphicalTile?

    /**
     * Returns this [Tile] as a [GraphicalTile] if possible or the
     * result of calling [orElse] with `this` if it is not.
     */
    fun asGraphicalTileOrElse(orElse: (Tile) -> GraphicalTile): GraphicalTile {
        return asGraphicalTileOrNull() ?: orElse(this)
    }

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

        private val DEFAULT_CHARACTER_TILE: CharacterTile = DefaultCharacterTile(
            character = ' ',
            styleSet = StyleSet.defaultStyle()
        )

        private val EMPTY_CHARACTER_TILE: CharacterTile = DefaultCharacterTile(
            character = ' ',
            styleSet = StyleSet.empty()
        )

    }
}
