package org.hexworks.zircon.internal.tileset.impl

import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.tileset.TextureContext
import org.hexworks.zircon.api.tileset.TextureTransformer
import kotlin.reflect.KClass

class DefaultTextureTransformer<T : Any, C : Any>(
    override val targetType: KClass<T>,
    private val transformFunction: (texture: TextureContext<T, C>, tile: Tile) -> TextureContext<T, C>
) : TextureTransformer<T, C> {
    override fun apply(texture: TextureContext<T, C>, tile: Tile) = transformFunction(texture, tile)
}
