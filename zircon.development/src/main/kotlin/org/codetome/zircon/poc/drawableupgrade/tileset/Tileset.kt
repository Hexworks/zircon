package org.codetome.zircon.poc.drawableupgrade.tileset

import org.codetome.zircon.poc.drawableupgrade.texture.TileTexture
import org.codetome.zircon.poc.drawableupgrade.tile.Tile

interface Tileset<T: Any, S: Any> {

    fun width(): Int

    fun height(): Int

    fun supportsTile(tile: Tile<out Any>): Boolean

    fun fetchTextureForTile(tile: Tile<T>): TileTexture<S>
}
