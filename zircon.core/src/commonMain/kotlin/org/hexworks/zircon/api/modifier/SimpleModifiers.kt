package org.hexworks.zircon.api.modifier

/**
 * Represents the built-in SimpleModifiers supported by zircon.
 */
sealed class SimpleModifiers : TextureTransformModifier {

    /**
     * Displays an underline below the tile.
     */
    object Underline : SimpleModifiers()

    /**
     * Will make the tile blink.
     */
    object Blink : SimpleModifiers()

    /**
     * Displays a horizontal line in the middle of the tile (from left to right).
     */
    object CrossedOut : SimpleModifiers()

    object VerticalFlip : SimpleModifiers()
    object HorizontalFlip : SimpleModifiers()

    /**
     * Prevents the tile content from rendering (will only render the background).
     */
    object Hidden : SimpleModifiers()

    override val cacheKey: String
        get() = "Modifier.${this::class.simpleName}"
}
