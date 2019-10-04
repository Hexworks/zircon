package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.behavior.TilesetHolder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.api.resource.TilesetResource

interface GraphicalTile : Tile, TilesetHolder {

    val name: String

    val tags: Set<String>

    override val tileset: TilesetResource

    fun withName(name: String): GraphicalTile

    fun withTags(tags: Set<String>): GraphicalTile

    override fun withForegroundColor(foregroundColor: TileColor): GraphicalTile

    override fun withBackgroundColor(backgroundColor: TileColor): GraphicalTile

    override fun withStyle(style: StyleSet): GraphicalTile

    override fun withModifiers(modifiers: Set<Modifier>): GraphicalTile

    override fun withModifiers(vararg modifiers: Modifier): GraphicalTile

    override fun withAddedModifiers(modifiers: Set<Modifier>): GraphicalTile

    override fun withAddedModifiers(vararg modifiers: Modifier): GraphicalTile

    override fun withRemovedModifiers(modifiers: Set<Modifier>): GraphicalTile

    override fun withRemovedModifiers(vararg modifiers: Modifier): GraphicalTile

    override fun withNoModifiers(): GraphicalTile

}
