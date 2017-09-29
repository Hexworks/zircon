package org.codetome.zircon.internal

import org.codetome.zircon.api.Modifier
import org.codetome.zircon.api.Modifiers.BorderPosition
import org.codetome.zircon.api.Modifiers.BorderPosition.*
import org.codetome.zircon.api.Modifiers.BorderType

/**
 * Represents the built-in BuiltInModifiers supported by zircon.
 */
sealed class BuiltInModifiers : Modifier {

    object Underline : BuiltInModifiers()
    object Blink : BuiltInModifiers()
    object CrossedOut : BuiltInModifiers()
    object VerticalFlip : BuiltInModifiers()
    object HorizontalFlip : BuiltInModifiers()
    object Hidden : BuiltInModifiers()
    object Bold : BuiltInModifiers()
    object Italic : BuiltInModifiers()
    data class Border(val borderType: BorderType,
                      val borderPositions: Set<BorderPosition>) : BuiltInModifiers() {

        /**
         * Creates a new [Border] which has its border positions added to this border.
         */
        operator fun plus(other: Border): Border {
            return Border(borderType, borderPositions.plus(other.borderPositions))
        }


    }

    object BorderFactory {

        /**
         * Creates a new [Border] with the given type and positions.
         */
        @JvmOverloads
        fun create(borderType: BorderType = BorderType.SOLID,
                   vararg borderPositions: BorderPosition = listOf(TOP, RIGHT, BOTTOM, LEFT).toTypedArray()) =
                Border(borderType, borderPositions.toSet())
    }


}