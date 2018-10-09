package org.hexworks.zircon.api.builder.modifier

import org.hexworks.zircon.api.builder.Builder
import org.hexworks.zircon.api.modifier.RayShade

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

    fun withOpacity(opacity: Float) = also {
        this.opacity = opacity
    }

    fun withThreshold(threshold: Float) = also {
        this.threshold = threshold
    }

    fun withStrength(strength: Float) = also {
        this.strength = strength
    }

    fun withRaysOnly(raysOnly: Boolean) = also {
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
