package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.behavior.Cacheable
import org.hexworks.zircon.api.behavior.Copiable
import org.hexworks.zircon.api.behavior.TilesetHolder
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.graphics.DrawSurface
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.data.DefaultCharacterTile

/**
 * A [Tile] is the basic building block that can be drawn on the screen.
 * It is a rectangular graphic, or character that can also be drawn on
 * [DrawSurface]s using [DrawSurface.draw].
 */
sealed class Tile : Cacheable {

    /**
     * The type of this [Tile]:
     * - [TileType.CHARACTER_TILE]
     * - [TileType.GRAPHICAL_TILE]
     * - [TileType.IMAGE_TILE]
     * @see TileType
     */
    abstract val tileType: TileType

    companion object {

        /**
         * Shorthand for the default character which is:
         * - a space character
         * - with default foreground
         * - and default background
         * - and no modifiers.
         */
        fun defaultTile(): CharacterTile = DEFAULT_CHARACTER_TILE

        /**
         * Shorthand for an empty character tile which is:
         * - a space character
         * - with transparent foreground
         * - and transparent background
         * - and no modifiers.
         */
        fun empty(): CharacterTile = EMPTY_CHARACTER_TILE

        private val DEFAULT_CHARACTER_TILE: CharacterTile = DefaultCharacterTile(
            character = ' ',
            styleSet = StyleSet.defaultStyle()
        )

        private val EMPTY_CHARACTER_TILE: CharacterTile = DefaultCharacterTile(
            character = ' ',
            styleSet = StyleSet.empty()
        )

    }
}

data class CharacterTile(
    val character: Char,
    val styleSet: StyleSet = StyleSet.defaultStyle(),
) : Tile(), StyleSet {
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
            characterTile {
                character = this@BaseCharacterTile.character
                styleSet = this@BaseCharacterTile.styleSet.withForegroundColor(foregroundColor)
            }
        }
    }

    override fun withBackgroundColor(backgroundColor: Color): CharacterTile {
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
                styleSet = style
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
                styleSet = this@BaseCharacterTile.styleSet.withModifiers(modifiers)
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
                styleSet = this@BaseCharacterTile.styleSet.withAddedModifiers(modifiers)
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
                styleSet = this@BaseCharacterTile.styleSet.withRemovedModifiers(modifiers)
            }
        }
    }

    override fun withNoModifiers(): CharacterTile {
        return if (this.modifiers.isEmpty()) {
            this
        } else {
            return characterTile {
                character = this@BaseCharacterTile.character
                styleSet = this@BaseCharacterTile.styleSet.withNoModifiers()
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

data class ImageTile(
    override val tileset: TilesetResource,
    val name: String
) : Tile(), TilesetHolder, Copiable<ImageTile> {

    override val tileType: TileType = TileType.IMAGE_TILE
    override val cacheKey: String
        get() = "ImageTile(t=${tileset.path},n=$name)"

    override fun createCopy(): ImageTile = copy()

    fun withName(name: String) = imageTile {
        this.name = name
        tileset = this@BaseImageTile.tileset
    }

    fun withTileset(tileset: TilesetResource) = imageTile {
        name = this@BaseImageTile.name
        this.tileset = tileset
    }
}

data class GraphicalTile(
    override val tileset: TilesetResource,
    val name: String,
    val tags: Set<String>,
) : Tile(), TilesetHolder, Copiable<GraphicalTile> {

    override val tileType: TileType = TileType.GRAPHICAL_TILE

    override val cacheKey: String
        get() = "GraphicTile(n=$name,t=[${tags.asSequence().sorted().joinToString()}])"

    override fun createCopy(): GraphicalTile = copy()

    fun withName(name: String) = graphicalTile {
        this.name = name
        tileset = this@BaseImageTile.tileset
    }

    fun withTileset(tileset: TilesetResource) = graphicalTile {
        name = this@BaseImageTile.name
        this.tileset = tileset
    }

    fun withTags(tags: Set<String>) = graphicalTile {
        name = this@BaseGraphicalTile.name
        tileset = this@BaseGraphicalTile.tileset
        this.tags = tags
    }
}

