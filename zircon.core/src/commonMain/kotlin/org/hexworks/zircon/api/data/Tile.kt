package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.behavior.Cacheable
import org.hexworks.zircon.api.behavior.HasModifiers
import org.hexworks.zircon.api.data.tile.CharacterTile
import org.hexworks.zircon.api.graphics.DrawSurface
import org.hexworks.zircon.api.graphics.StyleSet

/**
 * A [Tile] is the basic building block that can be drawn on the screen.
 * It is a rectangular graphic, or character that can also be drawn on
 * [DrawSurface]s using [DrawSurface.draw].
 */
interface Tile : Cacheable, HasModifiers<Tile> {

    /**
     * The type of this [Tile]:
     * - [TileType.CHARACTER_TILE]
     * - [TileType.GRAPHICAL_TILE]
     * - [TileType.IMAGE_TILE]
     * @see TileType
     */
    val tileType: TileType

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

        private val DEFAULT_CHARACTER_TILE: CharacterTile = CharacterTile(
            character = ' ',
            styleSet = StyleSet.defaultStyle()
        )

        private val EMPTY_CHARACTER_TILE: CharacterTile = CharacterTile(
            character = ' ',
            styleSet = StyleSet.empty()
        )

    }
}

