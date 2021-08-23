package org.hexworks.zircon.api.resource.base

import org.hexworks.cobalt.core.api.UUID
import org.hexworks.cobalt.core.platform.factory.UUIDFactory
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.resource.TilesetResource

/**
 * Base class for implementing [TilesetResource]s.
 */
abstract class BaseTilesetResource : TilesetResource {

    override val id: UUID = UUIDFactory.randomUUID()

    override fun toString(): String {
        return "TilesetResource(id=$id, size=$size, tileType=$tileType, tilesetType=$tilesetType, tilesetSourceType=$tilesetSourceType)"
    }

}
