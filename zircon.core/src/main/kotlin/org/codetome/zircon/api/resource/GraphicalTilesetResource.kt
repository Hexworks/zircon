package org.codetome.zircon.api.resource

import org.codetome.zircon.api.data.ImageTile
import org.codetome.zircon.api.util.Identifier
import kotlin.reflect.KClass

enum class GraphicalTilesetResource(private val tilesetName: String,
                                    override val width: Int,
                                    override val height: Int,
                                    private val fileName: String = "${tilesetName}_${width}x$height.zip",
                                    override val id: Identifier = Identifier.randomIdentifier(),
                                    override val tileType: KClass<ImageTile> = ImageTile::class,
                                    override val path: String = "src/main/resources/graphic_tilesets/$fileName")
    : TilesetResource<ImageTile> {

    NETHACK_16X16("nethack", 16, 16)

}
