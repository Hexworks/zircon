package org.codetome.zircon.api.modifier

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

    override fun generateCacheKey(): String {
        return "SimpleModifiers:${this::class.simpleName}"
    }
}
