package org.hexworks.zircon.api.modifier

import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Tile

/**
 * The built-in modifiers that need no parameters.
 */
public sealed class SimpleModifiers : TileModifier<Tile> {

    /**
     * Makes the tile blink. Blink timing lives in the renderer (it needs a clock), so this
     * modifier is a marker: [transform] leaves the tile untouched.
     */
    public object Blink : SimpleModifiers() {
        override fun transform(tile: Tile): Tile = tile
    }

    /**
     * Prevents the tile content from rendering: only the background is drawn.
     */
    public object Hidden : SimpleModifiers() {
        override fun canTransform(tile: Tile): Boolean = tile is CharacterTile

        override fun transform(tile: Tile): Tile = when (tile) {
            is CharacterTile -> tile.withCharacter(' ')
            else -> tile
        }
    }

    override val cacheKey: String
        get() = "Modifier.${this::class.simpleName}"

    override fun toString(): String = cacheKey
}
