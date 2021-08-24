package org.hexworks.zircon.api.tileset

import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.modifier.TextureTransformModifier
import kotlin.reflect.KClass

/**
 * Transforms a tile texture. A [TileTexture] is a part of a tileset sprite sheet or other
 * tileset source that represents a [Tile].
 *
 * Each [TextureTransformer] corresponds to a [TextureTransformModifier]. The transformation
 * of a [Tile] will only happen if [Tile.modifiers] contains the corresponding [supportedModifier].
 */
interface TextureTransformer<T : Any> {

    /**
     * The type of the texture that will be transformed by [transform]
     */
    val targetType: KClass<T>

    /**
     * Transforms a tileset texture and returns the transformed version.
     */
    fun transform(texture: TileTexture<T>, tile: Tile): TileTexture<T>
}
