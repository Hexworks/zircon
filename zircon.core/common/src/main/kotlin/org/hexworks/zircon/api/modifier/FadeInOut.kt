package org.hexworks.zircon.api.modifier

import org.hexworks.zircon.api.data.tile.CharacterTile
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.modifier.impl.Fade
import org.hexworks.zircon.platform.util.SystemUtils

data class FadeInOut(private val stepsFadeIn: Int = 20,
                     private val timeMsFadeIn: Long = 2000,
                     private val glowOnFinalFadeInStep: Boolean = false,
                     private val timeMsBeforeFadingOut: Long = 5000,
                     private val stepsFadeOut: Int = 20,
                     private val timeMsFadeOut: Long = 2000) : TileTransformModifier<CharacterTile>, Fade {

    private var currentFadeMode = FadeInOutMode.FadeIn
    private val fadeIn = FadeIn(stepsFadeIn, timeMsFadeIn, glowOnFinalFadeInStep)
    private val fadeOut = FadeOut(stepsFadeOut, timeMsFadeOut)
    private var startNoFadingRender: Long = Long.MIN_VALUE

    override fun isFadingFinished(): Boolean {
        return fadeOut.isFadingFinished()
    }

    override fun generateCacheKey(): String {
        return when (currentFadeMode) {
            FadeInOutMode.FadeIn -> fadeIn.generateCacheKey()
            FadeInOutMode.NoFading -> "Modifier.FadeInOut.($currentFadeMode)"
            FadeInOutMode.FadeOut -> fadeOut.generateCacheKey()
        }
    }

    override fun canTransform(tile: Tile) = tile is CharacterTile

    override fun transform(tile: CharacterTile): CharacterTile {
        currentFadeMode = getFadeMode()

        return when (currentFadeMode) {
            FadeInOutMode.FadeIn -> fadeIn.transform(tile)
            FadeInOutMode.NoFading -> {
                if (startNoFadingRender == Long.MIN_VALUE)
                    startNoFadingRender = SystemUtils.getCurrentTimeMs()
                tile
            }
            FadeInOutMode.FadeOut -> fadeOut.transform(tile)
        }

    }

    private fun getFadeMode(): FadeInOutMode {
        return when (currentFadeMode) {
            FadeInOutMode.FadeIn -> if (fadeIn.isFadingFinished()) FadeInOutMode.NoFading else FadeInOutMode.FadeIn
            FadeInOutMode.NoFading -> if ((SystemUtils.getCurrentTimeMs() - startNoFadingRender) > timeMsBeforeFadingOut)
                FadeInOutMode.FadeOut else FadeInOutMode.NoFading
            FadeInOutMode.FadeOut -> FadeInOutMode.FadeOut
        }
    }


}

internal enum class FadeInOutMode {
    FadeIn,
    NoFading,
    FadeOut
}
