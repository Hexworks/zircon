package org.hexworks.zircon.api.tileset

/**
 * Represents the texture which is used to draw
 * tiles for a given [Tileset].
 */
interface TileTexture<out T> {

    fun getWidth(): Int

    fun getHeight(): Int

    fun getTexture(): T
}
