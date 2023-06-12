package org.hexworks.zircon.internal.resource

import org.hexworks.zircon.api.resource.ResourceType
import org.hexworks.zircon.api.resource.base.BaseTilesetResource

/**
 * Tileset resource for true type fonts which can be used with
 * [org.hexworks.zircon.api.data.CharacterTile]s.
 */
internal class TrueTypeTilesetResource(
    override val width: Int,
    override val height: Int,
    override val path: String,
    override val resourceType: ResourceType,
    val name: String = ""
) : BaseTilesetResource() {

    override val tileType = TileType.CHARACTER_TILE
    override val tilesetType: TilesetType = TilesetType.TrueTypeFont

    override fun toString() = name.ifBlank { super.toString() }
}
