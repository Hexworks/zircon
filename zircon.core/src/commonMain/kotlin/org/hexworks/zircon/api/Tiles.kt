package org.hexworks.zircon.api

import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Tile
import kotlin.jvm.JvmStatic

object Tiles {

    /**
     * Creates a new [TileBuilder] for creating [Tile]s.
     */
    @JvmStatic
    @Deprecated("Use Tile.newBuilder instead", replaceWith = ReplaceWith(
            "Tile.newBuilder()", "org.hexworks.zircon.api.data.Tile"))
    fun newBuilder() = TileBuilder()

    /**
     * Shorthand for the default character tile which is:
     * - a space character
     * - with default foreground
     * - and default background
     * - and no modifiers.
     */
    @JvmStatic
    @Deprecated("Use Tile.defaultTile instead", replaceWith = ReplaceWith(
            "Tile.defaultTile()", "org.hexworks.zircon.api.data.Tile"))
    fun defaultTile(): CharacterTile = Tile.defaultTile()

    /**
     * Shorthand for an empty character tile which is:
     * - a space character
     * - with transparent foreground
     * - and transparent background
     * - and no modifiers.
     */
    @JvmStatic
    @Deprecated("Use Tile.empty instead", replaceWith = ReplaceWith(
            "Tile.empty()", "org.hexworks.zircon.api.data.Tile"))
    fun empty(): CharacterTile = Tile.empty()
}
