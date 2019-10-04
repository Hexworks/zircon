package org.hexworks.zircon.internal.resource

import org.hexworks.zircon.api.data.ImageTile

/**
 * Tileset resource class which can be used with [ImageTile]s.
 */
internal class ImageDictionaryTilesetResource(
        override val width: Int = 1,
        override val height: Int = 1,
        override val path: String,
        override val tilesetSourceType: TilesetSourceType) : BaseTilesetResource() {

    override val tileType: TileType = TileType.IMAGE_TILE
    override val tilesetType: TilesetType = TilesetType.GRAPHIC_TILESET
}
