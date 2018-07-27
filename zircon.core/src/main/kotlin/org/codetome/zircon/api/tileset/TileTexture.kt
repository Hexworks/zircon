package org.codetome.zircon.api.tileset

import org.codetome.zircon.api.behavior.Cacheable

/**
 * Represents the texture which is used to draw
 * characters for a given [Tileset].
 */
interface TileTexture<out T> : Cacheable {

    fun getBackend(): T
}
