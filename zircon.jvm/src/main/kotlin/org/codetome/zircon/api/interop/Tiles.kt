package org.codetome.zircon.api.interop

import org.codetome.zircon.api.builder.data.TileBuilder
import org.codetome.zircon.api.data.Tile

object Tiles {

    /**
     * Creates a new [TileBuilder] for creating [Tile]s.
     */
    @JvmStatic
    fun newBuilder() = TileBuilder()

    /**
     * Shorthand for the default character which is:
     * - a space character
     * - with default foreground
     * - and default background
     * - and no modifiers.
     */
    @JvmStatic
    fun defaultTile() = Tile.defaultTile()

    /**
     * Shorthand for an empty character which is:
     * - a space character
     * - with transparent foreground
     * - and transparent background
     * - and no modifiers.
     */
    @JvmStatic
    fun empty() = Tile.empty()
}
