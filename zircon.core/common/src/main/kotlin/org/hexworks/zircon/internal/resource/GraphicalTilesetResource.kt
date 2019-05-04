package org.hexworks.zircon.internal.resource

import org.hexworks.zircon.api.data.tile.GraphicalTile

/**
 * Tileset resource class which can be used with [GraphicalTile]s.
 */
internal class GraphicalTilesetResource(
        override val width: Int,
        override val height: Int,
        override val path: String,
        override val tilesetSourceType: TilesetSourceType) : BaseTilesetResource() {

    override val tileType = TileType.GRAPHIC_TILE
    override val tilesetType: TilesetType = TilesetType.GRAPHIC_TILESET
}
