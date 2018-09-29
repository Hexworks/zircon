package org.hexworks.zircon.api.modifier

data class RayShade(val opacity: Float = 1.0f,
                    val threshold: Float = 0.0f,
                    val strength: Float = 0.5f,
                    val raysOnly: Boolean = false) : TextureTransformModifier {

    override fun generateCacheKey(): String = "Modifier.RayShade($opacity,$threshold,$strength,$raysOnly)"
}
