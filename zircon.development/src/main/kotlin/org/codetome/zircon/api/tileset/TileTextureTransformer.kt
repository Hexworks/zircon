package org.codetome.zircon.api.tileset

import org.codetome.zircon.api.data.Tile

interface TileTextureTransformer<T> {

    /**
     * Transforms a tileset texture and returns the transformed version.
     */
    fun transform(texture: TileTexture<T>, tile: Tile<out Any>): TileTexture<T>
}
