package org.hexworks.zircon.internal.resource

import org.hexworks.zircon.api.data.GraphicalTile
import org.hexworks.zircon.api.resource.base.BaseTilesetResource
import org.hexworks.zircon.internal.resource.TilesetType.GraphicalTileset

/**
 * Tileset resource class which can be used with [GraphicalTile]s.
 */
internal class GraphicalTilesetResource(
    override val width: Int,
    override val height: Int,
    override val path: String,
    override val tilesetSourceType: TilesetSourceType
) : BaseTilesetResource() {

    override val tileType = TileType.GRAPHICAL_TILE
    override val tilesetType = GraphicalTileset
}
