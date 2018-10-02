package org.hexworks.zircon.api.tileset

/**
 * Represents the texture which is used to draw
 * tiles for a given [Tileset].
 */
interface TileTexture<out T> {

    fun width(): Int

    fun height(): Int

    /**
     * Returns the actual texture.
     */
    fun texture(): T
}
