package org.hexworks.zircon.internal.tileset

import org.hexworks.zircon.api.resource.CP437TilesetResource
import org.hexworks.zircon.api.resource.GraphicalTilesetResource
import org.hexworks.zircon.api.resource.TilesetResource
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

object ImageLoader {

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
            resource is CP437TilesetResource || resource is GraphicalTilesetResource
}
