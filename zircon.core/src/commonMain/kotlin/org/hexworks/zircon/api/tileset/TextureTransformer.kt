package org.hexworks.zircon.api.tileset

import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.modifier.TextureModifier
import kotlin.reflect.KClass

/**
 * Transforms a tile texture. A [TextureContext] is a part of a tileset sprite sheet or other
 * tileset source that represents a [Tile].
 *
 * Each [TextureTransformer] corresponds to a [TextureModifier]. The transformation
 * of a [Tile] will only happen if [Tile.modifiers] contains the corresponding modifier.
 *
 * @param T the texture object that the renderer will draw on (like Graphics2D in Swing)
 * @param C the rendering context that contains all related objects that are necessary for rendering
 */
public interface TextureTransformer<T : Any, C : Any> {

    /**
     * The type of the texture that will be modified by [apply]
     */
    public val targetType: KClass<T>

    /**
     * Takes a tileset texture and applies the transformation to it.
     * 📙 Note that this operation is potentially drawing onto an existing texture
     * (probably in video memory), so some modifications will not work
     * (for example cropping, flipping)
     */
    public fun apply(texture: TextureContext<T, C>, tile: Tile): TextureContext<T, C>
}
