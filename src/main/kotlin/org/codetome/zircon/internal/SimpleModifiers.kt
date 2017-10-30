package org.codetome.zircon.internal

import org.codetome.zircon.api.Modifier

/**
 * Represents the built-in SimpleModifiers supported by zircon.
 */
sealed class SimpleModifiers : Modifier {

    object Underline : SimpleModifiers()
    object Blink : SimpleModifiers()
    object CrossedOut : SimpleModifiers()
    object VerticalFlip : SimpleModifiers()
    object HorizontalFlip : SimpleModifiers()
    object Hidden : SimpleModifiers()
    object Bold : SimpleModifiers()
    object Italic : SimpleModifiers()
    object Glow : SimpleModifiers()

    override fun generateCacheKey(): String = this.javaClass.simpleName
}