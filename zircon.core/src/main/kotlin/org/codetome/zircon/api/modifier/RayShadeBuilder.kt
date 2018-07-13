package org.codetome.zircon.api.modifier

import org.codetome.zircon.api.builder.Builder

/**
 * Builds [RayShade]s.
 * Defaults:
 * - opacity: 1
 * - threshold: 0
 * - strength: 0.5
 * - rays only: false
 */
data class RayShadeBuilder(var opacity: Float = 1.0f,
                           var threshold: Float = 0.0f,
                           var strength: Float = 0.5f,
                           var raysOnly: Boolean = false)
    : Builder<RayShade> {

    override fun build(): RayShade = RayShade(
            opacity = opacity,
            threshold = threshold,
            strength = strength,
            raysOnly = raysOnly)

    override fun createCopy() = copy()

    fun opacity(opacity: Float) = also {
        this.opacity = opacity
    }

    fun threshold(threshold: Float) = also {
        this.threshold = threshold
    }

    fun strength(strength: Float) = also {
        this.strength = strength
    }

    fun raysOnly(raysOnly: Boolean) = also {
        this.raysOnly = raysOnly
    }

    companion object {

        /**
         * Creates a new [RayShadeBuilder] for creating [RayShade]s.
         */
        fun newBuilder() = RayShadeBuilder()

        /**
         * Shorthand for the default ray shade which is:
         * - opacity: 1
         * - threshold: 0
         * - strength: 0.5
         * - rays only: false
         */
        val DEFAULT_RAY_SHADE = newBuilder().build()
    }
}
