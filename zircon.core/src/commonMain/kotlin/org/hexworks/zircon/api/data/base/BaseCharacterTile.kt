package org.hexworks.zircon.api.data.base

import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.internal.resource.TileType

/**
 * Base class for [CharacterTile]s.
 */
abstract class BaseCharacterTile : BaseTile(), CharacterTile {

    override val tileType: TileType
        get() = TileType.CHARACTER_TILE

    override val foregroundColor: TileColor
        get() = styleSet.foregroundColor

    override val backgroundColor: TileColor
        get() = styleSet.backgroundColor

    override val modifiers: Set<Modifier>
        get() = styleSet.modifiers

    override fun withForegroundColor(foregroundColor: TileColor): CharacterTile {
        return if (this.foregroundColor == foregroundColor) {
            this
        } else {
            characterTile {
                character = this@BaseCharacterTile.character
                styleSet = this@BaseCharacterTile.styleSet.withForegroundColor(foregroundColor)
            }
        }
    }

    override fun withBackgroundColor(backgroundColor: TileColor): CharacterTile {
        return if (this.backgroundColor == backgroundColor) {
            this
        } else {
            characterTile {
                character = this@BaseCharacterTile.character
                styleSet = this@BaseCharacterTile.styleSet.withBackgroundColor(backgroundColor)
            }
        }
    }

    override fun withStyle(style: StyleSet): CharacterTile {
        return if (this.styleSet == style) {
            this
        } else {
            characterTile {
                character = this@BaseCharacterTile.character
                this.styleSet = style
            }
        }
    }

    override fun withModifiers(vararg modifiers: Modifier): CharacterTile =
        withModifiers(modifiers.toSet())

    override fun withModifiers(modifiers: Set<Modifier>): CharacterTile {
        return if (this.modifiers == modifiers) {
            this
        } else {
            return characterTile {
                character = this@BaseCharacterTile.character
                this.styleSet = styleSet.withModifiers(modifiers)
            }
        }
    }

    override fun withAddedModifiers(vararg modifiers: Modifier): CharacterTile =
        withAddedModifiers(modifiers.toSet())

    override fun withAddedModifiers(modifiers: Set<Modifier>): CharacterTile {
        return if (this.modifiers.containsAll(modifiers)) {
            this
        } else {
            return characterTile {
                character = this@BaseCharacterTile.character
                this.styleSet = styleSet.withAddedModifiers(modifiers)
            }
        }
    }

    override fun withRemovedModifiers(vararg modifiers: Modifier): CharacterTile =
        withRemovedModifiers(modifiers.toSet())

    override fun withRemovedModifiers(modifiers: Set<Modifier>): CharacterTile {
        return if (this.modifiers.intersect(modifiers).isEmpty()) {
            this
        } else {
            return characterTile {
                character = this@BaseCharacterTile.character
                this.styleSet = styleSet.withRemovedModifiers(modifiers)
            }
        }
    }

    override fun withNoModifiers(): CharacterTile {
        return if (this.modifiers.isEmpty()) {
            this
        } else {
            return characterTile {
                character = this@BaseCharacterTile.character
                this.styleSet = styleSet.withNoModifiers()
            }
        }
    }

    override fun withCharacter(character: Char): CharacterTile {
        return if (this.character == character) {
            this
        } else {
            return characterTile {
                this.character = character
                styleSet = this@BaseCharacterTile.styleSet
            }
        }
    }
}
