package org.hexworks.zircon.api

import org.hexworks.zircon.api.builder.modifier.BorderBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.modifier.*
import org.hexworks.zircon.api.modifier.SimpleModifiers.*
import org.hexworks.zircon.internal.modifier.TileCoordinate
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

/**
 * Represents the built-in modifiers supported by zircon.
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

    @JvmStatic
    fun crop(x: Int, y: Int, width: Int, height: Int): Modifier = Crop(x, y, width, height)

    @JvmStatic
    @JvmOverloads
    fun fadeIn(steps: Int = 20,
               timeMs: Long = 2000,
               glowOnFinalStep: Boolean = true): Modifier = FadeIn(steps, timeMs, glowOnFinalStep)

    @JvmStatic
    @JvmOverloads
    fun fadeOut(steps: Int = 20,
                timeMs: Long = 2000): Modifier = FadeOut(steps, timeMs)

    @JvmStatic
    @JvmOverloads
    fun fadeInOut(stepsFadeIn: Int = 20,
                  timeMsFadeIn: Long = 2000,
                  glowOnFinalFadeInStep: Boolean = false,
                  timeMsBeforeFadingOut: Long = 5000,
                  stepsFadeOut: Int = 20,
                  timeMsFadeOut: Long = 2000): Modifier = FadeInOut(stepsFadeIn, timeMsFadeIn, glowOnFinalFadeInStep, timeMsBeforeFadingOut,
            stepsFadeOut, timeMsFadeOut)

    @JvmStatic
    fun delay(timeMs: Long): Modifier = Delay(timeMs)

}
