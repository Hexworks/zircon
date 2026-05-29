package org.hexworks.zircon.internal.resource

import org.hexworks.zircon.api.data.TileType.GRAPHICAL_TILE
import org.hexworks.zircon.api.resource.ResourceType
import org.hexworks.zircon.api.resource.TilesetType.GraphicalTileset
import org.hexworks.zircon.api.resource.base.BaseTilesetResource

/**
 * Tileset resource class which can be used with [org.hexworks.zircon.api.data.tile.GraphicalTile]s.
 */
internal class GraphicalTilesetResource(
    override val width: Int,
    override val height: Int,
    override val path: String,
    override val resourceType: ResourceType
) : BaseTilesetResource() {

    override val tileType = GRAPHICAL_TILE
    override val tilesetType = GraphicalTileset
}
