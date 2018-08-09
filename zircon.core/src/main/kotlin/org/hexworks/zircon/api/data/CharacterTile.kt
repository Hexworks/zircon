package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.behavior.Drawable
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.Modifier

data class CharacterTile(
        val character: Char,
        private val style: StyleSet = StyleSet.defaultStyle())
    : Drawable, Tile {

    private val cacheKey = "CharacterTile(c=$character,s=${style.generateCacheKey()})"

    override fun tileType() = CharacterTile::class

    override fun generateCacheKey() = cacheKey

    override fun styleSet() = style

    override fun withForegroundColor(foregroundColor: TileColor): Tile {
        return if (this.getForegroundColor() == foregroundColor) {
            this
        } else {
            Tile.create(character, style.withForegroundColor(foregroundColor))
        }
    }

    override fun withBackgroundColor(backgroundColor: TileColor): Tile {
        return if (this.getBackgroundColor() == backgroundColor) {
            this
        } else {
            Tile.create(character, style.withBackgroundColor(backgroundColor))
        }
    }

    override fun withStyle(style: StyleSet): Tile {
        return if (this.style == style) {
            this
        } else {
            Tile.create(character, style)
        }
    }

    override fun withModifiers(modifiers: Set<Modifier>): Tile {
        return if (modifiers == this.getModifiers()) {
            this
        } else {
            return Tile.create(character, style.withModifiers(modifiers))
        }
    }

    override fun withoutModifiers(modifiers: Set<Modifier>): Tile {
        return if (getModifiers().intersect(modifiers).isEmpty()) {
            this
        } else {
            Tile.create(character, style.withRemovedModifiers(modifiers))
        }
    }

    fun withCharacter(character: Char): CharacterTile {
        return if (this.character == character) {
            this
        } else {
            Tile.create(character, style)
        }
    }
}
