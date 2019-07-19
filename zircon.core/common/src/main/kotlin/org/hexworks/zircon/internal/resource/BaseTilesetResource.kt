package org.hexworks.zircon.internal.resource

import org.hexworks.cobalt.Identifier
import org.hexworks.cobalt.factory.IdentifierFactory
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.resource.TilesetResource

/**
 * Contains metadata about a tileset for a given [Tile] type.
 */
internal abstract class BaseTilesetResource : TilesetResource {

    override val id: Identifier = IdentifierFactory.randomIdentifier()

    override fun isCompatibleWith(other: TilesetResource): Boolean {
        return other.tileType == tileType &&
                other.width == width &&
                other.height == height
    }
}
