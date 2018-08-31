package org.hexworks.zircon.api.resource

import org.hexworks.zircon.api.data.GraphicTile
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.internal.resource.BaseTilesetResource

/**
 * Tileset resource class which can be used with [GraphicTile]s.
 */
class GraphicalTilesetResource(override val width: Int,
                               override val height: Int,
                               override val path: String) : BaseTilesetResource() {

    override val tileType = TileType.GRAPHIC_TILE
    override val tilesetType: TilesetType = TilesetType.GRAPHICAL_TILESET
}
