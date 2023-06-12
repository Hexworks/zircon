package org.hexworks.zircon.internal.resource

import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.resource.ResourceType
import org.hexworks.zircon.api.resource.base.BaseTilesetResource
import org.hexworks.zircon.internal.resource.TilesetType.CP437Tileset

/**
 * CP437 Tileset resource class which can be used with [CharacterTile]s.
 */
internal class CP437TilesetResource(
    override val width: Int,
    override val height: Int,
    override val path: String,
    override val resourceType: ResourceType
) : BaseTilesetResource() {

    override val tileType = TileType.CHARACTER_TILE
    override val tilesetType: TilesetType = CP437Tileset
}
