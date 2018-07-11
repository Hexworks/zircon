package org.codetome.zircon.api.interop

import org.codetome.zircon.api.modifier.BorderBuilder
import org.codetome.zircon.internal.SimpleModifiers.*

/**
 * Represents the built-in modifiers supported by zircon.
 */
object Modifiers {
    @JvmField
    val UNDERLINE = Underline

    @JvmField
    val BLINK = Blink

    @JvmField
    val CROSSED_OUT = CrossedOut

    @JvmField
    val VERTICAL_FLIP = VerticalFlip

    @JvmField
    val HORIZONTAL_FLIP = HorizontalFlip

    @JvmField
    val HIDDEN = Hidden

    @JvmField
    val BOLD = Bold

    @JvmField
    val ITALIC = Italic

    @JvmField
    val GLOW = Glow

    /**
     * Shorthand for the default border which is:
     * - a simple border
     * - on all sides (top, right, bottom, left)
     * @see BorderBuilder if you want to create custom borders
     */
    @JvmField
    val BORDER = BorderBuilder.newBuilder().build()
}
