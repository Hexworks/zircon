package org.codetome.zircon.api.interop

import org.codetome.zircon.api.modifier.Border
import org.codetome.zircon.api.modifier.BorderBuilder
import org.codetome.zircon.api.modifier.SimpleModifiers.*

/**
 * Represents the built-in modifiers supported by zircon.
 */
object Modifiers {

    @JvmStatic
    fun underline() = Underline

    @JvmStatic
    fun blink() = Blink

    @JvmStatic
    fun crossedOut() = CrossedOut

    @JvmStatic
    fun verticalFlip() = VerticalFlip

    @JvmStatic
    fun horizontalFlip() = HorizontalFlip

    @JvmStatic
    fun hidden() = Hidden

    @JvmStatic
    fun bold() = Bold

    @JvmStatic
    fun italic() = Italic

    @JvmStatic
    fun glow() = Glow

    /**
     * Shorthand for the default border which is:
     * - a simple border
     * - on all sides (top, right, bottom, left)
     * @see BorderBuilder if you want to create custom borders
     */
    @JvmStatic
    fun border(): Border = BorderBuilder.newBuilder().build()
}
