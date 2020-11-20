package org.hexworks.zircon.api.modifier

/**
 * Adds a glow effect to a tile.
 * @param radius the radius of the glow in pixels.
 */
data class Glow(val radius: Float = 5.0f) : TextureTransformModifier {

    override val cacheKey: String
        get() = "Modifier.Glow($radius)"
}
