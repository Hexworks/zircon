package org.codetome.zircon.api.modifier

import org.codetome.zircon.api.Modifier

data class RayShade(val opacity: Float = 1.0f,
                    val threshold: Float = 0.0f,
                    val strength: Float = 0.5f,
                    val raysOnly: Boolean = false) : Modifier {

    private val cacheKey = StringBuilder().apply {
        append(javaClass.simpleName)
        append(opacity)
        append("-")
        append(threshold)
        append("-")
        append(strength)
        append("-")
        append(raysOnly)
    }.toString()

    override fun generateCacheKey() = cacheKey
}