package org.hexworks.zircon.api.modifier

data class Crop(val x: Int,
                val y: Int,
                val width: Int,
                val height: Int) : TextureTransformModifier {

    override fun generateCacheKey(): String = "Modifier.Crop($x,$y,$width,$height)"
}
