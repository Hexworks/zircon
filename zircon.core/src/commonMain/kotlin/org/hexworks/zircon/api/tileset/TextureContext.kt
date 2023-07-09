package org.hexworks.zircon.api.tileset

import org.hexworks.zircon.api.behavior.Cacheable

/**
 * Represents the texture with the draw context that can be used to draw tiles for a given [Tileset].
 */
data class TextureContext<T : Any, C : Any>(
    override val cacheKey: String,
    val width: Int,
    val height: Int,
    val texture: T,
    val context: C,
) : Cacheable
