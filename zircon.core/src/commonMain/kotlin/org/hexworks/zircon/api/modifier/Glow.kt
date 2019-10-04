package org.hexworks.zircon.api.modifier

data class Glow(val radius: Float = 5.0f) : TextureTransformModifier {

    override val cacheKey: String
        get() = "Modifier.Glow($radius)"
}
