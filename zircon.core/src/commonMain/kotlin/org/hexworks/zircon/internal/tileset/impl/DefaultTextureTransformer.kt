package org.hexworks.zircon.internal.tileset.impl

import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.tileset.TextureTransformer
import org.hexworks.zircon.api.tileset.TileTexture
import kotlin.reflect.KClass

class DefaultTextureTransformer<T : Any>(
    override val targetType: KClass<T>,
    private val transformFunction: (texture: TileTexture<T>, tile: Tile) -> TileTexture<T>
) : TextureTransformer<T> {
    override fun transform(texture: TileTexture<T>, tile: Tile) = transformFunction(texture, tile)
}
