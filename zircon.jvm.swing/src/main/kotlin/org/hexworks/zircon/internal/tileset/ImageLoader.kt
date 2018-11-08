package org.hexworks.zircon.internal.tileset

import org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource
import org.hexworks.zircon.api.resource.BuiltInGraphicTilesetResource
import org.hexworks.zircon.api.resource.TilesetResource
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

object ImageLoader {

    // TODO: regression test this
    fun readImage(resource: TilesetResource): BufferedImage {
        return if (isInternalResource(resource)) {
            // this loads it from the zircon jar
            ImageIO.read(this::class.java.getResource(resource.path))
        } else {
            // and this loads it from an external source
            ImageIO.read(File(resource.path))
        }
    }

    private fun isInternalResource(resource: TilesetResource) =
            resource is BuiltInCP437TilesetResource || resource is BuiltInGraphicTilesetResource
}
