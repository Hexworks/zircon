package org.hexworks.zircon.api.tileset

import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.internal.behavior.Identifiable
import kotlin.reflect.KClass

/**
 * A [Tileset] contains the textures of a tileset that can be drawn on the screen.
 * Each tileset has a [targetType] that specifies the object type which will be used
 * when [drawTile] is called. For example in case of Swing [targetType] will be
 * a `Graphics2D` object.
 * @param S the class of the surface that we'll draw upon (Graphics2D for Swing for example)
 */
interface Tileset<S : Any> : Identifiable {

    /**
     * The type of the target surface the textures are drawn upon.
     */
    val targetType: KClass<S>

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
    fun drawTile(tile: Tile, surface: S, position: Position)
}
