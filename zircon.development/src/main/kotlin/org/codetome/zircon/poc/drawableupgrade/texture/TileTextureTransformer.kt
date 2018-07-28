package org.codetome.zircon.poc.drawableupgrade.texture

import org.codetome.zircon.poc.drawableupgrade.tile.Tile

interface TileTextureTransformer<T> {

    /**
     * Transforms a tileset texture and returns the transformed version.
     */
    fun transform(texture: TileTexture<T>, tile: Tile<out Any>): TileTexture<T>
}
