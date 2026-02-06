package org.hexworks.zircon.api

import org.hexworks.zircon.api.modifier.*
import org.hexworks.zircon.api.modifier.SimpleModifiers.Blink
import org.hexworks.zircon.api.modifier.SimpleModifiers.Hidden

/**
 * This object contains factory methods for the [Modifier]s supported by Zircon.
 */
object Modifiers {

    fun blink(): Modifier = Blink

    fun hidden(): Modifier = Hidden

    /**
     * Provides a fade in effect for the tile that has this modifier.
     * The effect will start when the tile is added to a tile grid.
     * @param steps the number of frames for the fade effect
     * @param timeMs the time (in milliseconds) the effect takes
     * when the effect finishes.
     */
    fun fadeIn(
        steps: Int = 20,
        timeMs: Long = 2000,
    ): Modifier = FadeIn(steps, timeMs)

    /**
     * Provides a fade out effect for the tile that has this modifier.
     * The effect will start when the tile is added to a tile grid.
     * @param steps the number of frames for the fade effect
     * @param timeMs the time (in milliseconds) the effect takes
     */
    fun fadeOut(
        steps: Int = 20,
        timeMs: Long = 2000
    ): Modifier = FadeOut(steps, timeMs)

    // TODO: move all of this into a builder
    fun fadeInOut(
        stepsFadeIn: Int = 20,
        timeMsFadeIn: Long = 2000,
        timeMsBeforeFadingOut: Long = 5000,
        stepsFadeOut: Int = 20,
        timeMsFadeOut: Long = 2000
    ): Modifier = FadeInOut(
        stepsFadeIn, timeMsFadeIn, timeMsBeforeFadingOut,
        stepsFadeOut, timeMsFadeOut
    )

    /**
     * Keeps the tile that has this modifier hidden until [timeMs] milliseconds pass
     * after adding the tile to a grid.
     */
    fun delay(timeMs: Long): Modifier = Delay(timeMs)

}
