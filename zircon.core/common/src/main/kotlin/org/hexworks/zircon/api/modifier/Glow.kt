package org.hexworks.zircon.api.modifier

data class Glow(val radius: Float = 5.0f) : TextureTransformModifier {

    override fun generateCacheKey(): String = "Modifier.Glow($radius)"
}
