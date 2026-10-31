package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.behavior.HasStyle
import org.hexworks.zircon.api.behavior.TilesetHolder
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.TileType
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.api.resource.TilesetResource

/**
 * A [Tile] that draws a single [character] with a [styleSet].
 *
 * A [CharacterTile] normally draws with the tileset of the layer it sits on, so its
 * [tileset] is [TilesetResource.Companion.UNKNOWN]. A tile that carries its own [tileset] (see
 * [withTileset]) draws with that one instead, wherever it is placed. A frame captured
 * with `TileGrid.captureFrame()` uses this to keep the look of layers that use another
 * tileset than the grid.
 */
public data class CharacterTile(
    val character: Char,
    override val styleSet: StyleSet = StyleSet.defaultStyle(),
    override val tileset: TilesetResource = TilesetResource.Companion.UNKNOWN,
) : Tile, StyleSet, HasStyle<CharacterTile>, TilesetHolder {
    override val tileType: TileType = TileType.CHARACTER_TILE

    override val cacheKey: String
        get() = if (tileset.isUnknown) {
            "CharacterTile(c=$character,s=${styleSet.cacheKey})"
        } else {
            "CharacterTile(c=$character,s=${styleSet.cacheKey},t=${tileset.id})"
        }

    override val foregroundColor: Color
        get() = styleSet.foregroundColor

    override val backgroundColor: Color
        get() = styleSet.backgroundColor

    override val modifiers: Set<Modifier>
        get() = styleSet.modifiers

    override fun createCopy(): CharacterTile = copy()

    /**
     * Returns a copy of this tile that draws with [tileset] instead of the tileset of
     * the layer it sits on. Pass [TilesetResource.Companion.UNKNOWN] to draw with the layer's again.
     */
    public fun withTileset(tileset: TilesetResource): CharacterTile {
        return if (this.tileset === tileset) {
            this
        } else {
            copy(tileset = tileset)
        }
    }

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

    public fun withCharacter(character: Char): CharacterTile {
        return if (this.character == character) {
            this
        } else {
            copy(character = character)
        }
    }
}

