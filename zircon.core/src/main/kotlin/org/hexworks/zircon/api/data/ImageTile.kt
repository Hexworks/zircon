package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.behavior.TilesetOverride
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.api.resource.TilesetResource

interface ImageTile : Tile, TilesetOverride {

    val tileset: TilesetResource
    val name: String

    fun withName(name: String): ImageTile

    override fun withForegroundColor(foregroundColor: TileColor): ImageTile

    override fun withBackgroundColor(backgroundColor: TileColor): ImageTile

    override fun withStyle(style: StyleSet): ImageTile

    override fun withModifiers(modifiers: Set<Modifier>): ImageTile

    override fun withModifiers(vararg modifiers: Modifier): ImageTile

    override fun withAddedModifiers(modifiers: Set<Modifier>): ImageTile

    override fun withAddedModifiers(vararg modifiers: Modifier): ImageTile

    override fun withRemovedModifiers(modifiers: Set<Modifier>): ImageTile

    override fun withRemovedModifiers(vararg modifiers: Modifier): ImageTile

    override fun withNoModifiers(): ImageTile

}
