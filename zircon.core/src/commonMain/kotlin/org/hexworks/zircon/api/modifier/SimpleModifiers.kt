package org.hexworks.zircon.api.modifier

import org.hexworks.zircon.api.data.Tile

/**
 * Represents the built-in SimpleModifiers supported by zircon.
 */
sealed class SimpleModifiers : TileModifier<Tile> {

    /**
     * Will make the tile blink.
     */
    object Blink : SimpleModifiers() {
        override fun transform(tile: Tile): Tile {
            TODO("Not yet implemented")
        }
    }

    /**
     * Prevents the tile content from rendering (will only render the background).
     */
    object Hidden : SimpleModifiers() {
        override fun transform(tile: Tile): Tile {
            TODO("Not yet implemented")
        }
    }

    override val cacheKey: String
        get() = "Modifier.${this::class.simpleName}"

    override fun toString() = cacheKey
}
