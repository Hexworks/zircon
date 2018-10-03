package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.api.resource.TileType

interface CharacterTile : Tile {

    val character: Char

    override val tileType: TileType
        get() = TileType.CHARACTER_TILE

    override fun withForegroundColor(foregroundColor: TileColor): CharacterTile {
        return if (this.foregroundColor == foregroundColor) {
            this
        } else {
            Tile.createCharacterTile(character, styleSet.withForegroundColor(foregroundColor))
        }
    }

    override fun withBackgroundColor(backgroundColor: TileColor): CharacterTile {
        return if (this.backgroundColor == backgroundColor) {
            this
        } else {
            Tile.createCharacterTile(character, styleSet.withBackgroundColor(backgroundColor))
        }
    }

    override fun withStyle(style: StyleSet): CharacterTile {
        return if (this.styleSet == style) {
            this
        } else {
            Tile.createCharacterTile(character, style)
        }
    }

    override fun withModifiers(vararg modifiers: Modifier) = withModifiers(modifiers.toSet())

    override fun withModifiers(modifiers: Set<Modifier>): CharacterTile {
        return if (this.modifiers == modifiers) {
            this
        } else {
            return Tile.createCharacterTile(character, styleSet.withModifiers(modifiers))
        }
    }

    override fun withoutModifiers(modifiers: Set<Modifier>): CharacterTile {
        return if (this.modifiers.intersect(modifiers).isEmpty()) {
            this
        } else {
            Tile.createCharacterTile(character, styleSet.withRemovedModifiers(modifiers))
        }
    }

    fun withCharacter(character: Char): CharacterTile {
        return if (this.character == character) {
            this
        } else {
            Tile.createCharacterTile(character, styleSet)
        }
    }
}
