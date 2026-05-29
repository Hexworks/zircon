package org.hexworks.zircon.internal.resource

import org.hexworks.zircon.api.data.TileType
import org.hexworks.zircon.api.resource.ResourceType
import org.hexworks.zircon.api.resource.TilesetType
import org.hexworks.zircon.api.resource.base.BaseTilesetResource

/**
 * CP437 Tileset resource class which can be used with [org.hexworks.zircon.api.data.tile.CharacterTile]s.
 */
internal class CP437TilesetResource(
    override val width: Int,
    override val height: Int,
    override val path: String,
    override val resourceType: ResourceType
) : BaseTilesetResource() {

    override val tileType = TileType.CHARACTER_TILE
    override val tilesetType: TilesetType = TilesetType.CP437Tileset
}
