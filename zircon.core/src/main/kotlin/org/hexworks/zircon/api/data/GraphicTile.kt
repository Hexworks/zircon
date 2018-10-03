package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.api.resource.TileType

interface GraphicTile : Tile {

    val name: String
    val tags: Set<String>

    override val tileType: TileType
        get() = TileType.GRAPHIC_TILE

    override val styleSet: StyleSet
        get() = StyleSet.defaultStyle()

    fun withName(name: String): GraphicTile

    fun withTags(tags: Set<String>): GraphicTile

    override fun withForegroundColor(foregroundColor: TileColor) = this

    override fun withBackgroundColor(backgroundColor: TileColor) = this

    override fun withStyle(style: StyleSet) = this

    override fun withModifiers(modifiers: Set<Modifier>) = this

    override fun withoutModifiers(modifiers: Set<Modifier>) = this

}
