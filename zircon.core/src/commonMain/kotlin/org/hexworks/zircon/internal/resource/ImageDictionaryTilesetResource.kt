package org.hexworks.zircon.internal.resource

import org.hexworks.zircon.api.data.ImageTile
import org.hexworks.zircon.api.resource.ResourceType
import org.hexworks.zircon.api.resource.base.BaseTilesetResource
import org.hexworks.zircon.internal.resource.TilesetType.GraphicalTileset

/**
 * Tileset resource class which can be used with [ImageTile]s.
 */
internal class ImageDictionaryTilesetResource(
    override val width: Int = 1,
    override val height: Int = 1,
    override val path: String,
    override val resourceType: ResourceType
) : BaseTilesetResource() {

    override val tileType = TileType.IMAGE_TILE
    override val tilesetType = GraphicalTileset
}
