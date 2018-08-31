package org.hexworks.zircon.api.resource

import org.hexworks.zircon.api.data.GraphicTile
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.internal.resource.BaseTilesetResource

/**
 * Tileset resource class which can be used with [GraphicTile]s.
 */
class MonospaceFontResource(override val width: Int,
                            override val height: Int,
                            override val path: String) : BaseTilesetResource() {

    override val tileType = TileType.CHARACTER_TILE
    override val tilesetType: TilesetType = TilesetType.TRUE_TYPE_FONT
}
