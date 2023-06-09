package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.behavior.OptTilesetHolder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.api.resource.TilesetResource

/**
 * Specialization of a [Tile] which uses a [Char].
 */
interface CharacterTile : Tile, OptTilesetHolder {

    override val tileset: TilesetResource? get() = null

    val character: Char

    fun withCharacter(character: Char): CharacterTile

    override fun withForegroundColor(foregroundColor: TileColor): CharacterTile

    override fun withBackgroundColor(backgroundColor: TileColor): CharacterTile

    override fun withStyle(style: StyleSet): CharacterTile

    override fun withModifiers(vararg modifiers: Modifier): CharacterTile

    override fun withModifiers(modifiers: Set<Modifier>): CharacterTile

    override fun withAddedModifiers(vararg modifiers: Modifier): CharacterTile

    override fun withAddedModifiers(modifiers: Set<Modifier>): CharacterTile

    override fun withRemovedModifiers(vararg modifiers: Modifier): CharacterTile

    override fun withRemovedModifiers(modifiers: Set<Modifier>): CharacterTile

    override fun withNoModifiers(): CharacterTile

}
