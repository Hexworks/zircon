package org.hexworks.zircon.api.resource

import org.hexworks.cobalt.datatypes.Identifier
import org.hexworks.cobalt.datatypes.factory.IdentifierFactory

enum class BuiltInGraphicTilesetResource(private val tilesetName: String,
                                         override val width: Int,
                                         override val height: Int,
                                         private val fileName: String = "${tilesetName}_${width}x$height.zip",
                                         override val id: Identifier = IdentifierFactory.randomIdentifier(),
                                         override val tileType: TileType = TileType.GRAPHIC_TILE,
                                         override val tilesetType: TilesetType = TilesetType.GRAPHIC_TILESET,
                                         override val path: String = "/graphic_tilesets/$fileName")
    : TilesetResource {

    NETHACK_16X16("nethack", 16, 16)

}
