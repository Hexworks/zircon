package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.behavior.TilesetOverride
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.api.resource.TilesetResource

interface ImageTile
    : Tile, TilesetOverride {

    val tileset: TilesetResource
    val name: String

    override fun tileType() = ImageTile::class.simpleName!!

    override fun styleSet() = StyleSet.defaultStyle()

    fun withName(name: String) = Tile.createImageTile(
            name = name,
            tileset = tileset,
            style = styleSet())

    override fun withForegroundColor(foregroundColor: TileColor) = this

    override fun withBackgroundColor(backgroundColor: TileColor) = this

    override fun withStyle(style: StyleSet) = this

    override fun withModifiers(modifiers: Set<Modifier>) = this

    override fun withoutModifiers(modifiers: Set<Modifier>) = this

}
