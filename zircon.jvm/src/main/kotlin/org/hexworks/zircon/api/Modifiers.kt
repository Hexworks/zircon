package org.hexworks.zircon.api

import org.hexworks.zircon.api.modifier.Border
import org.hexworks.zircon.api.builder.modifier.BorderBuilder
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.api.modifier.SimpleModifiers.*

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
    fun glow(): Modifier = Glow

    /**
     * Shorthand for the default border which is:
     * - a simple border
     * - on all sides (top, right, bottom, left)
     * @see BorderBuilder if you want to create custom borders
     */
    @JvmStatic
    fun border(): Border = BorderBuilder.newBuilder().build()
}
