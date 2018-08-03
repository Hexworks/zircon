package org.codetome.zircon.api.tileset

import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.internal.behavior.Identifiable
import kotlin.reflect.KClass

interface Tileset<T: Tile, S: Any> : Identifiable {

    val sourceType: KClass<S>

    fun width(): Int

    fun height(): Int

    fun getSize() = Size.create(width(), height())

    fun supportsTile(tile: Tile): Boolean

    fun fetchTextureForTile(tile: T): TileTexture<S>
}
