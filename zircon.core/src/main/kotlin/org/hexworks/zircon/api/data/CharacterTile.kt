package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.api.resource.TileType

interface CharacterTile : Tile {

    val character: Char

    override fun tileType(): TileType = TileType.CHARACTER_TILE

    override fun withForegroundColor(foregroundColor: TileColor): CharacterTile {
        return if (this.getForegroundColor() == foregroundColor) {
            this
        } else {
            Tile.createCharacterTile(character, styleSet().withForegroundColor(foregroundColor))
        }
    }

    override fun withBackgroundColor(backgroundColor: TileColor): CharacterTile {
        return if (this.getBackgroundColor() == backgroundColor) {
            this
        } else {
            Tile.createCharacterTile(character, styleSet().withBackgroundColor(backgroundColor))
        }
    }

    override fun withStyle(style: StyleSet): CharacterTile {
        return if (this.styleSet() == style) {
            this
        } else {
            Tile.createCharacterTile(character, style)
        }
    }

    override fun withModifiers(modifiers: Set<Modifier>): CharacterTile {
        return if (modifiers == this.getModifiers()) {
            this
        } else {
            return Tile.createCharacterTile(character, styleSet().withModifiers(modifiers))
        }
    }

    override fun withoutModifiers(modifiers: Set<Modifier>): CharacterTile {
        return if (getModifiers().intersect(modifiers).isEmpty()) {
            this
        } else {
            Tile.createCharacterTile(character, styleSet().withRemovedModifiers(modifiers))
        }
    }

    fun withCharacter(character: Char): CharacterTile {
        return if (this.character == character) {
            this
        } else {
            Tile.createCharacterTile(character, styleSet())
        }
    }
}
