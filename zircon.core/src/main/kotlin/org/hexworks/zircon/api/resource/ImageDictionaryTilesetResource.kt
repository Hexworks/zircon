package org.hexworks.zircon.api.resource

import org.hexworks.zircon.api.data.ImageTile
import org.hexworks.zircon.internal.resource.BaseTilesetResource
import kotlin.reflect.KClass

/**
 * Tileset resource class which can be used with [ImageTile]s.
 */
class ImageDictionaryTilesetResource(override val path: String,
                                     override val width: Int = 1,
                                     override val height: Int = 1) : BaseTilesetResource() {

    override val tileType: TileType = TileType.IMAGE_TILE
    override val tilesetType: TilesetType = TilesetType.GRAPHICAL_TILESET
}
