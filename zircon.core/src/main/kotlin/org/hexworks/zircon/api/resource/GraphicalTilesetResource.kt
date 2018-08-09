package org.hexworks.zircon.api.resource

import org.hexworks.zircon.api.data.GraphicTile
import org.hexworks.zircon.api.util.Identifier
import kotlin.reflect.KClass

enum class GraphicalTilesetResource(private val tilesetName: String,
                                    override val width: Int,
                                    override val height: Int,
                                    private val fileName: String = "${tilesetName}_${width}x$height.zip",
                                    override val id: Identifier = Identifier.randomIdentifier(),
                                    override val tileType: KClass<GraphicTile> = GraphicTile::class,
                                    override val path: String = "zircon.core/src/main/resources/graphic_tilesets/$fileName")
    : TilesetResource {

    NETHACK_16X16("nethack", 16, 16)

}
