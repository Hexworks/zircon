package org.hexworks.zircon.internal.resource

import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.util.Identifier

/**
 * Contains metadata about a tileset for a given [Tile] type.
 */
abstract class BaseTilesetResource : TilesetResource {

    override val id: Identifier = Identifier.randomIdentifier()

    override fun size() = Size.create(width, height)

    override fun isCompatibleWith(other: TilesetResource): Boolean {
        return other.tileType == tileType &&
                other.width == width &&
                other.height == height
    }
}
