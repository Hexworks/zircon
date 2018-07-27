package org.codetome.zircon.api.tileset

import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.internal.behavior.Identifiable

/**
 * A tileset contains textures for [Tile]s.
 */
interface Tileset : Identifiable {

    /**
     * Returns the width of a character in pixels.
     */
    fun getWidth(): Int

    /**
     * Returns the height of a character in pixels.
     */
    fun getHeight(): Int

    /**
     * Tells whether this [Tileset] knows about the given [Char] or not.
     */
    fun hasDataForChar(char: Char): Boolean

    /**
     * Returns a region (graphical representation of a [Tile]) for a character.
     * If `tags` are supplied in `tile`, than this method will try to filter for them.
     * *Note that* this is only useful for graphical tilesets which have multiple
     * regions for a given [Tile]!
     */
    fun fetchRegionForChar(tile: Tile): TileTexture<*>

    /**
     * Returns all the [TileTextureMetadata] for a [Char] which is known by this [Tileset].
     */
    fun fetchMetadataForChar(char: Char): List<TileTextureMetadata>

    /**
     * Returns the `Size` of this `Tileset` (width, height)
     */
    fun getSize(): Size = Size.create(getWidth(), getHeight())
}
