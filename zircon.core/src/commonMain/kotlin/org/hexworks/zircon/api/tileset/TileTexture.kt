package org.hexworks.zircon.api.tileset

/**
 * Represents the texture which is used to draw
 * tiles for a given [Tileset].
 */
interface TileTexture<out T> {

    val width: Int
    val height: Int
    /**
     * The actual texture.
     */
    val texture: T
}
