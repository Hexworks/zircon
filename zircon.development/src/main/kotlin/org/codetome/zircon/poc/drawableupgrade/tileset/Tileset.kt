package org.codetome.zircon.poc.drawableupgrade.tileset

import org.codetome.zircon.poc.drawableupgrade.Tile

interface Tileset<T> {

    fun getWidth(): Int

    fun getHeight(): Int

    fun supportsTile(tile: Tile): Boolean

    fun fetchTextureForTile(tile: Tile): TileTexture<T>
}
