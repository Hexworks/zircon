package org.hexworks.zircon.api.modifier

import korlibs.time.DateTime
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.modifier.impl.Fade

data class FadeInOut(
    private val stepsFadeIn: Int = 20,
    private val timeMsFadeIn: Long = 2000,
    private val timeMsBeforeFadingOut: Long = 5000,
    private val stepsFadeOut: Int = 20,
    private val timeMsFadeOut: Long = 2000
) : TileTransformModifier<CharacterTile>, Fade {

    override fun isFadingFinished(): Boolean {
        return fadeOut.isFadingFinished()
    }

    override val cacheKey: String
        get() = when (currentFadeMode) {
            FadeInOutMode.FadeIn -> fadeIn.cacheKey
            FadeInOutMode.NoFading -> "Modifier.FadeInOut.($currentFadeMode)"
            FadeInOutMode.FadeOut -> fadeOut.cacheKey
        }

    private var currentFadeMode = FadeInOutMode.FadeIn
    private val fadeIn = FadeIn(stepsFadeIn, timeMsFadeIn)
    private val fadeOut = FadeOut(stepsFadeOut, timeMsFadeOut)
    private var startNoFadingRender: Long = Long.MIN_VALUE

    override fun canTransform(tile: Tile) = tile is CharacterTile

    override fun transform(tile: CharacterTile): CharacterTile {
        currentFadeMode = getFadeMode()

        return when (currentFadeMode) {
            FadeInOutMode.FadeIn -> fadeIn.transform(tile)
            FadeInOutMode.NoFading -> {
                if (startNoFadingRender == Long.MIN_VALUE)
                    startNoFadingRender = DateTime.nowUnixMillisLong()
                tile
            }

            FadeInOutMode.FadeOut -> fadeOut.transform(tile)
        }

    }

    private fun getFadeMode(): FadeInOutMode {
        return when (currentFadeMode) {
            FadeInOutMode.FadeIn -> if (fadeIn.isFadingFinished()) FadeInOutMode.NoFading else FadeInOutMode.FadeIn
            FadeInOutMode.NoFading -> if ((DateTime.nowUnixMillisLong() - startNoFadingRender) > timeMsBeforeFadingOut)
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
