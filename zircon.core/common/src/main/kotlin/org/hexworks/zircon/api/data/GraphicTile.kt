package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.behavior.TilesetOverride
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.Tileset

interface GraphicTile : Tile, TilesetOverride {

    val name: String
    val tags: Set<String>
    val tileset: TilesetResource

    fun withName(name: String): GraphicTile

    fun withTags(tags: Set<String>): GraphicTile

    override fun withForegroundColor(foregroundColor: TileColor): GraphicTile

    override fun withBackgroundColor(backgroundColor: TileColor): GraphicTile

    override fun withStyle(style: StyleSet): GraphicTile

    override fun withModifiers(modifiers: Set<Modifier>): GraphicTile

    override fun withModifiers(vararg modifiers: Modifier): GraphicTile

    override fun withAddedModifiers(modifiers: Set<Modifier>): GraphicTile

    override fun withAddedModifiers(vararg modifiers: Modifier): GraphicTile

    override fun withRemovedModifiers(modifiers: Set<Modifier>): GraphicTile

    override fun withRemovedModifiers(vararg modifiers: Modifier): GraphicTile

    override fun withNoModifiers(): GraphicTile

}
