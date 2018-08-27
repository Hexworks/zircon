package org.hexworks.zircon.api.resource

import org.hexworks.zircon.api.data.GraphicTile
import org.hexworks.zircon.api.util.Identifier
import kotlin.reflect.KClass

enum class BuiltInGraphicalTilesetResource(private val tilesetName: String,
                                           override val width: Int,
                                           override val height: Int,
                                           private val fileName: String = "${tilesetName}_${width}x$height.zip",
                                           override val id: Identifier = Identifier.randomIdentifier(),
                                           override val tileType: KClass<GraphicTile> = GraphicTile::class,
                                           override val path: String = "/graphic_tilesets/$fileName")
    : TilesetResource {

    NETHACK_16X16("nethack", 16, 16)

}
