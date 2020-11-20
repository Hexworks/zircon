package org.hexworks.zircon.api.data.base

import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.ImageTile
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.internal.resource.TileType
import org.hexworks.zircon.api.resource.TilesetResource

/**
 * Base class for [ImageTile]s.
 */
abstract class BaseImageTile : BaseTile(), ImageTile {

    override val foregroundColor: TileColor
        get() = TileColor.transparent()

    override val backgroundColor: TileColor
        get() = TileColor.transparent()

    override val modifiers: Set<Modifier>
        get() = setOf()

    override val tileType: TileType
        get() = TileType.IMAGE_TILE

    override val styleSet: StyleSet
        get() = StyleSet.empty()

    override fun withName(name: String) = Tile.createImageTile(
            name = name,
            tileset = tileset)

    override fun withTileset(tileset: TilesetResource) = Tile.createImageTile(
            name = name,
            tileset = tileset)

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

    override fun toBuilder() = Tile.newBuilder()
            .withName(name)
            .withTileset(tileset)
}
