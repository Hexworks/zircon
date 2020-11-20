package org.hexworks.zircon.api

import org.hexworks.zircon.api.builder.modifier.BorderBuilder
import org.hexworks.zircon.api.modifier.*
import org.hexworks.zircon.api.modifier.SimpleModifiers.*
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

/**
 * This object contains factory methods for the [Modifier]s supported by Zircon.
 */
object Modifiers {

    @JvmStatic
    fun underline(): Modifier = Underline

    @JvmStatic
    fun blink(): Modifier = Blink

    @JvmStatic
    fun crossedOut(): Modifier = CrossedOut

    @JvmStatic
    fun verticalFlip(): Modifier = VerticalFlip

    @JvmStatic
    fun horizontalFlip(): Modifier = HorizontalFlip

    @JvmStatic
    fun hidden(): Modifier = Hidden

    /**
     * Adds a glow effect to a tile.
     * @param radius the radius in pixels for the effect.
     */
    @JvmStatic
    @JvmOverloads
    fun glow(radius: Float = 5.0f): Modifier = Glow(radius)

    /**
     * Shorthand for the default border which is:
     * - a simple border
     * - on all sides (top, right, bottom, left)
     * @see BorderBuilder if you want to create custom borders
     */
    @JvmStatic
    fun border(): Border = BorderBuilder.newBuilder().build()

    /**
     * Crops the tile that contains this modifier. [Crop] takes
     * an [x],[y] position pair and the [width]+[height] that delimits
     * the are that will be **kept** from the source tile. Everything
     * else will be cropped.
     */
    @JvmStatic
    fun crop(x: Int, y: Int, width: Int, height: Int): Modifier = Crop(x, y, width, height)

    /**
     * Provides a fade in effect for the tile that has this modifier.
     * The effect will start when the tile is added to a tile grid.
     * @param steps the number of frames for the fade effect
     * @param timeMs the time (in milliseconds) the effect takes
     * @param glowOnFinalStep tells whether the tile will be given the [Glow] modifier
     * when the effect finishes.
     */
    @JvmStatic
    @JvmOverloads
    fun fadeIn(
            steps: Int = 20,
            timeMs: Long = 2000,
            glowOnFinalStep: Boolean = true
    ): Modifier = FadeIn(steps, timeMs, glowOnFinalStep)

    /**
     * Provides a fade out effect for the tile that has this modifier.
     * The effect will start when the tile is added to a tile grid.
     * @param steps the number of frames for the fade effect
     * @param timeMs the time (in milliseconds) the effect takes
     */
    @JvmStatic
    @JvmOverloads
    fun fadeOut(
            steps: Int = 20,
            timeMs: Long = 2000
    ): Modifier = FadeOut(steps, timeMs)

    // TODO: move all of this into a builder
    @JvmStatic
    @JvmOverloads
    fun fadeInOut(
            stepsFadeIn: Int = 20,
            timeMsFadeIn: Long = 2000,
            glowOnFinalFadeInStep: Boolean = false,
            timeMsBeforeFadingOut: Long = 5000,
            stepsFadeOut: Int = 20,
            timeMsFadeOut: Long = 2000
    ): Modifier = FadeInOut(stepsFadeIn, timeMsFadeIn, glowOnFinalFadeInStep, timeMsBeforeFadingOut,
            stepsFadeOut, timeMsFadeOut)

    /**
     * Keeps the tile that has this modifier hidden until [timeMs] milliseconds pass
     * after adding the tile to a grid.
     */
    @JvmStatic
    fun delay(timeMs: Long): Modifier = Delay(timeMs)

}
