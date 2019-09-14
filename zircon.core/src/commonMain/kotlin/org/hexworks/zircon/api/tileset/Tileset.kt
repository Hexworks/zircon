package org.hexworks.zircon.api.tileset

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.internal.behavior.Identifiable
import kotlin.reflect.KClass

/**
 * Handles the textures of a tileset, and provides
 * functionality to render them on a surface.
 */
interface Tileset<T : Any> : Identifiable {

    /**
     * The type of the target surface the textures are drawn.
     */
    val targetType: KClass<T>

    /**
     * The width of a texture in pixels.
     */
    val width: Int

    /**
     * The height of a texture in pixels.
     */
    val height: Int

    /**
     * width * height
     */
    val size: Size
        get() = Size.create(width, height)

    /**
     * Draws the given `tile` on the given `surface` at the
     * given `position`. Does nothing if the `tile` is not
     * supported by this [Tileset].
     */
    fun drawTile(tile: Tile, surface: T, position: Position)
}
