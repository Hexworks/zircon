package org.hexworks.zircon.internal.resource

import org.hexworks.cobalt.core.api.UUID
import org.hexworks.cobalt.core.platform.factory.UUIDFactory
import org.hexworks.zircon.api.resource.TilesetResource

internal enum class BuiltInGraphicalTilesetResource(
    private val tilesetName: String,
    override val width: Int,
    override val height: Int,
    private val fileName: String = "${tilesetName}_${width}x$height.zip",
    override val id: UUID = UUIDFactory.randomUUID(),
    override val tileType: TileType = TileType.GRAPHICAL_TILE,
    override val tilesetType: TilesetType = TilesetType.GraphicalTileset,
    override val path: String = "/graphical_tilesets/$fileName",
    override val tilesetSourceType: TilesetSourceType = TilesetSourceType.JAR
) : TilesetResource {

    NETHACK_16X16("nethack", 16, 16)

}
