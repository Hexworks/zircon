package org.hexworks.zircon.api.data

import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.Modifier

interface GraphicTile : Tile {

    val name: String
    val tags: Set<String>

    fun withName(name: String): GraphicTile

    fun withTags(tags: Set<String>): GraphicTile

    override fun styleSet() = StyleSet.defaultStyle()

    override fun withForegroundColor(foregroundColor: TileColor) = this

    override fun withBackgroundColor(backgroundColor: TileColor) = this

    override fun withStyle(style: StyleSet) = this

    override fun withModifiers(modifiers: Set<Modifier>) = this

    override fun withoutModifiers(modifiers: Set<Modifier>) = this

}
