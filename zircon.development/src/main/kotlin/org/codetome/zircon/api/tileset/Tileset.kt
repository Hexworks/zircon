package org.codetome.zircon.api.tileset

import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.data.Tile

interface Tileset<T: Any, S: Any> {

    fun width(): Int

    fun height(): Int

    fun getSize() = Size.create(width(), height())

    fun supportsTile(tile: Tile<out Any>): Boolean

    fun fetchTextureForTile(tile: Tile<T>): TileTexture<S>
}
