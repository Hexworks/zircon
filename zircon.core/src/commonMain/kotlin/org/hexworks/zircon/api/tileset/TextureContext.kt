package org.hexworks.zircon.api.tileset

import org.hexworks.zircon.api.behavior.Cacheable

/**
 * Represents the texture with the draw context that can be used to draw tiles
 * for a given [Tileset].
 * @param T the texture object that the renderer will draw on (like Graphics2D in Swing)
 * @param C the rendering context that contains all related objects that are necessary for rendering
 */
public data class TextureContext<T : Any, C : Any>(
    override val cacheKey: String,
    val width: Int,
    val height: Int,
    val texture: T,
    val context: C,
) : Cacheable
