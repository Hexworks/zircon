package org.hexworks.zircon.internal.resource

/**
 * Tileset resource for true type fonts which can be used with
 * [org.hexworks.zircon.api.data.CharacterTile]s.
 */
internal class TrueTypeTilesetResource(
        override val width: Int,
        override val height: Int,
        override val path: String,
        override val tilesetSourceType: TilesetSourceType,
        val name: String = ""
) : BaseTilesetResource() {

    override val tileType = TileType.CHARACTER_TILE
    override val tilesetType: TilesetType = TilesetType.TRUE_TYPE_FONT

    override fun toString() = if (name.isBlank()) super.toString() else name
}
