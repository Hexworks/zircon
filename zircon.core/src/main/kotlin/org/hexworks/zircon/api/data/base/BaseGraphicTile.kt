package org.hexworks.zircon.api.data.base

import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.GraphicTile
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.api.resource.TileType

abstract class BaseGraphicTile : BaseTile(), GraphicTile {

    override val foregroundColor: TileColor
        get() = TileColor.transparent()

    override val backgroundColor: TileColor
        get() = TileColor.transparent()

    override val modifiers: Set<Modifier>
        get() = setOf()

    override val tileType: TileType
        get() = TileType.GRAPHIC_TILE

    override val styleSet: StyleSet
        get() = StyleSet.empty()

    override fun withName(name: String): GraphicTile {
        return Tile.createGraphicTile(
                name = name,
                tags = tags)
    }

    override fun withTags(tags: Set<String>): GraphicTile {
        return Tile.createGraphicTile(
                name = name,
                tags = tags)
    }

    override fun withForegroundColor(foregroundColor: TileColor) = this

    override fun withBackgroundColor(backgroundColor: TileColor) = this

    override fun withStyle(style: StyleSet) = this

    override fun withModifiers(modifiers: Set<Modifier>) = this

    override fun withModifiers(vararg modifiers: Modifier) = this

    override fun withAddedModifiers(modifiers: Set<Modifier>) = this

    override fun withAddedModifiers(vararg modifiers: Modifier) = this

    override fun withRemovedModifiers(modifiers: Set<Modifier>) = this

    override fun withRemovedModifiers(vararg modifiers: Modifier) = this

    override fun withNoModifiers() = this
}
