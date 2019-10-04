package org.hexworks.zircon.internal.resource

import org.hexworks.cobalt.Identifier
import org.hexworks.cobalt.factory.IdentifierFactory
import org.hexworks.zircon.api.resource.TilesetResource

internal enum class BuiltInGraphicalTilesetResource(
        private val tilesetName: String,
        override val width: Int,
        override val height: Int,
        private val fileName: String = "${tilesetName}_${width}x$height.zip",
        override val id: Identifier = IdentifierFactory.randomIdentifier(),
        override val tileType: TileType = TileType.GRAPHIC_TILE,
        override val tilesetType: TilesetType = TilesetType.GRAPHIC_TILESET,
        override val path: String = "/graphic_tilesets/$fileName",
        override val tilesetSourceType: TilesetSourceType = TilesetSourceType.JAR)
    : TilesetResource {

    NETHACK_16X16("nethack", 16, 16)

}
