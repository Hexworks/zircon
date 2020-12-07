package org.hexworks.zircon.internal.resource

import org.hexworks.zircon.api.data.CharacterTile

/**
 * CP437 Tileset resource class which can be used with [CharacterTile]s.
 */
internal class CP437TilesetResource(
    override val width: Int,
    override val height: Int,
    override val path: String,
    override val tilesetSourceType: TilesetSourceType
) : BaseTilesetResource() {

    override val tileType = TileType.CHARACTER_TILE
    override val tilesetType: TilesetType = TilesetType.CP437_TILESET
}
