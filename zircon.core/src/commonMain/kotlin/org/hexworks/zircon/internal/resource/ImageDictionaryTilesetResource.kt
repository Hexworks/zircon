package org.hexworks.zircon.internal.resource

import org.hexworks.zircon.api.data.TileType.IMAGE_TILE
import org.hexworks.zircon.api.resource.ResourceType
import org.hexworks.zircon.api.resource.TilesetType.GraphicalTileset
import org.hexworks.zircon.api.resource.base.BaseTilesetResource

/**
 * Tileset resource class which can be used with [org.hexworks.zircon.api.data.tile.ImageTile]s.
 */
internal class ImageDictionaryTilesetResource(
    override val width: Int = 1,
    override val height: Int = 1,
    override val path: String,
    override val resourceType: ResourceType
) : BaseTilesetResource() {

    override val tileType = IMAGE_TILE
    override val tilesetType = GraphicalTileset
}
