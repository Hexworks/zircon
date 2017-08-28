package org.codetome.zircon.api

sealed class Modifiers : Modifier {

    object Underline : Modifiers()
    object Blink : Modifiers()
    object CrossedOut : Modifiers()
    object VerticalFlip : Modifiers()
    object HorizontalFlip : Modifiers()
    object Hidden : Modifiers()
    // disabled temporarily
    object Bold : Modifiers()

    object Italic : Modifiers()
    data class Border(val borderType: BorderType,
                      val borderPositions: Set<BorderPosition>) : Modifiers()

    enum class BorderType {
        SOLID
    }

    enum class BorderPosition {
        TOP, RIGHT, BOTTOM, LEFT
    }

    object BorderFactory {

        fun of(borderType: BorderType, vararg borderPositions: BorderPosition) =
                Border(borderType, borderPositions.toSet())
    }

    companion object {
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
    }
}