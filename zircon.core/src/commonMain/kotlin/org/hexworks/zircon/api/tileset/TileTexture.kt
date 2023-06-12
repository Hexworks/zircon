package org.hexworks.zircon.api.tileset

import org.hexworks.zircon.api.behavior.Cacheable

/**
 * Represents the texture which is used to draw tiles for a given [Tileset].
 */
interface TileTexture<out T> : Cacheable {

    val width: Int
    val height: Int

    /**
     * The actual texture.
     */
    val texture: T

}
