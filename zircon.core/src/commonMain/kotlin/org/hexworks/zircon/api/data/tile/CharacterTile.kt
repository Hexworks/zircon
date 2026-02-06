package org.hexworks.zircon.api.data.tile

import org.hexworks.zircon.api.behavior.HasStyle
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.TileType
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.Modifier

data class CharacterTile(
    val character: Char,
    override val styleSet: StyleSet = StyleSet.defaultStyle(),
) : Tile, StyleSet, HasStyle<CharacterTile> {
    override val tileType: TileType = TileType.CHARACTER_TILE

    override val cacheKey: String
        get() = "CharacterTile(c=$character,s=${styleSet.cacheKey})"

    override val foregroundColor: Color
        get() = styleSet.foregroundColor

    override val backgroundColor: Color
        get() = styleSet.backgroundColor

    override val modifiers: Set<Modifier>
        get() = styleSet.modifiers

    override fun createCopy(): CharacterTile = copy()

    override fun withForegroundColor(foregroundColor: Color): CharacterTile {
        return if (this.foregroundColor == foregroundColor) {
            this
        } else {
            copy(styleSet = styleSet.withForegroundColor(foregroundColor))
        }
    }

    override fun withBackgroundColor(backgroundColor: Color): CharacterTile {
        return if (this.backgroundColor == backgroundColor) {
            this
        } else {
            copy(styleSet = styleSet.withBackgroundColor(backgroundColor))
        }
    }

    override fun withStyle(style: StyleSet): CharacterTile {
        return if (this.styleSet == style) {
            this
        } else {
            copy(styleSet = style)
        }
    }

    override fun withModifiers(vararg modifiers: Modifier): CharacterTile =
        withModifiers(modifiers.toSet())

    override fun withModifiers(modifiers: Set<Modifier>): CharacterTile {
        return if (this.modifiers == modifiers) {
            this
        } else {
            copy(styleSet = styleSet.withModifiers(modifiers))
        }
    }

    override fun withAddedModifiers(vararg modifiers: Modifier): CharacterTile =
        withAddedModifiers(modifiers.toSet())

    override fun withAddedModifiers(modifiers: Set<Modifier>): CharacterTile {
        return if (this.modifiers.containsAll(modifiers)) {
            this
        } else {
            copy(styleSet = styleSet.withAddedModifiers(modifiers))
        }
    }

    override fun withRemovedModifiers(vararg modifiers: Modifier): CharacterTile =
        withRemovedModifiers(modifiers.toSet())

    override fun withRemovedModifiers(modifiers: Set<Modifier>): CharacterTile {
        return if (this.modifiers.intersect(modifiers).isEmpty()) {
            this
        } else {
            copy(styleSet = styleSet.withRemovedModifiers(modifiers))
        }
    }

    override fun withNoModifiers(): CharacterTile {
        return if (this.modifiers.isEmpty()) {
            this
        } else {
            copy(styleSet = styleSet.withNoModifiers())
        }
    }

    fun withCharacter(character: Char): CharacterTile {
        return if (this.character == character) {
            this
        } else {
            copy(character = character)
        }
    }
}

