package org.hexworks.zircon.api.modifier

/**
 * Represents the built-in SimpleModifiers supported by zircon.
 */
sealed class SimpleModifiers : TextureTransformModifier {

    object Underline : SimpleModifiers()
    object Blink : SimpleModifiers()
    object CrossedOut : SimpleModifiers()
    object VerticalFlip : SimpleModifiers()
    object HorizontalFlip : SimpleModifiers()
    object Hidden : SimpleModifiers()

    override fun generateCacheKey(): String {
        return "Modifier.${this::class.simpleName}"
    }
}
