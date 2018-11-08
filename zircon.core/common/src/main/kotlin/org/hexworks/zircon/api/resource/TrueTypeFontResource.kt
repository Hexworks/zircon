package org.hexworks.zircon.api.resource

import org.hexworks.zircon.internal.resource.BaseTilesetResource

/**
 * Tileset resource for true type fonts which can be used with [org.hexworks.zircon.api.data.CharacterTile]s.
 */
class TrueTypeFontResource(override val width: Int,
                           override val height: Int,
                           override val path: String) : BaseTilesetResource() {

    override val tileType = TileType.CHARACTER_TILE
    override val tilesetType: TilesetType = TilesetType.TRUE_TYPE_FONT
}
