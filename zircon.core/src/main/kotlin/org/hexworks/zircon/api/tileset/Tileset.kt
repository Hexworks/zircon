package org.hexworks.zircon.api.tileset

import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.internal.behavior.Identifiable
import kotlin.reflect.KClass

/**
 * Handles the textures of a tileset, and provides
 * functionality to fetch them for rendering.
 */
interface Tileset<S : Any> : Identifiable {

    /**
     * The type of the textures this [Tileset] handles (eg.: BufferedImage)
     */
    val sourceType: KClass<S>

    /**
     * The width of a texture in pixels.
     */
    fun width(): Int

    /**
     * The height of a texture in pixels.
     */
    fun height(): Int

    /**
     * width * height
     */
    fun getSize() = Size.create(width(), height())

    /**
     * Tells whether the given [Tile] is supported by this [Tileset] or not.
     */
    fun supportsTile(tile: Tile): Boolean

    /**
     * Returns the [TileTexture] for the given [Tile].
     */
    fun fetchTextureForTile(tile: Tile): TileTexture<S>
}
