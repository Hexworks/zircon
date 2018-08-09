package org.hexworks.zircon.api

import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Tile

object Tiles {

    /**
     * Creates a new [TileBuilder] for creating [Tile]s.
     */
    @JvmStatic
    fun newBuilder() = TileBuilder()

    /**
     * Shorthand for the default character tile which is:
     * - a space character
     * - with default foreground
     * - and default background
     * - and no modifiers.
     */
    @JvmStatic
    fun defaultTile(): CharacterTile = Tile.defaultTile()

    /**
     * Shorthand for an empty character tile which is:
     * - a space character
     * - with transparent foreground
     * - and transparent background
     * - and no modifiers.
     */
    @JvmStatic
    fun empty(): CharacterTile = Tile.empty()
}
