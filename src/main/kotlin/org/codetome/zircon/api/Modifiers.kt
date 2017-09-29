package org.codetome.zircon.api

import org.codetome.zircon.internal.BuiltInModifiers.*

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
    val BORDER = BorderFactory

    @JvmField
    val BOLD = Bold

    @JvmField
    val ITALIC = Italic

    enum class BorderType {
        SOLID, DOTTED, DASHED
    }

    enum class BorderPosition {
        TOP, RIGHT, BOTTOM, LEFT
    }
}