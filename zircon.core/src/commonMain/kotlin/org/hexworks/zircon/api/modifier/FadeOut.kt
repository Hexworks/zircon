package org.hexworks.zircon.api.modifier

import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.modifier.impl.Fade
import org.hexworks.zircon.platform.util.SystemUtils

data class FadeOut(
        private val steps: Int = 20,
        private val timeMs: Long = 2000
) : TileTransformModifier<CharacterTile>, Fade {

    override val cacheKey: String
        get() = "Modifier.FadeOut.$currentStep"

    private var currentStep = 1
    private var delay: Long = timeMs / steps
    private var lastRender: Long = Long.MIN_VALUE

    override fun isFadingFinished(): Boolean {
        return currentStep == steps
    }


    override fun canTransform(tile: Tile) = tile is CharacterTile

    override fun transform(tile: CharacterTile): CharacterTile {
        if (isFirstRender()) {
            lastRender = SystemUtils.getCurrentTimeMs()
            generateTile(tile)
        }
        return if (currentStep == steps) {
            Tile.empty()
        } else {
            val now = SystemUtils.getCurrentTimeMs()
            if (now - lastRender > delay) {
                lastRender = now
                currentStep++
            }
            generateTile(tile)
        }
    }

    private fun generateTile(tile: CharacterTile): CharacterTile {
        return tile.withBackgroundColor(tile.backgroundColor
                .darkenByPercent(1.0.minus((steps - currentStep.toDouble()).div(steps))))
                .withForegroundColor(tile.foregroundColor
                        .darkenByPercent(1.0.minus((steps - currentStep.toDouble()).div(steps))))
    }

    private fun isFirstRender() = lastRender == Long.MIN_VALUE

}
