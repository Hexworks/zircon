package org.hexworks.zircon.api.tileset

import org.hexworks.zircon.api.data.Tile

/**
 * Transforms a tile texture. A [TileTexture] is a part of a
 * tileset sprite sheet or other tileset source which represents a [Tile].
 */
interface TextureTransformer<T> {

    /**
     * Transforms a tileset texture and returns the transformed version.
     */
    fun transform(texture: TileTexture<T>, tile: Tile): TileTexture<T>
}
