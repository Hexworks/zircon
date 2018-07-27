package org.codetome.zircon.api.tileset

import org.codetome.zircon.api.data.Tile

/**
 * Transforms a tileset region. A tileset region is a part of a
 * tileset sprite sheet or other tileset source which represents a character.
 */
interface TileTextureTransformer<T> {

    /**
     * Transforms a tileset texture and returns the transformed version.
     */
    fun transform(texture: TileTexture<T>, tile: Tile): TileTexture<T>
}
