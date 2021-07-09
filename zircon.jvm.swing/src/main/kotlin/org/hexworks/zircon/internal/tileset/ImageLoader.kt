package org.hexworks.zircon.internal.tileset

import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.resource.TilesetSourceType
import java.awt.image.BufferedImage
import java.io.File
import java.io.InputStream
import javax.imageio.ImageIO

object ImageLoader {

    fun readImage(resource: TilesetResource): BufferedImage {
        return if (isJarResource(resource)) {
            val res =
                this::class.java.getResource(resource.path) ?: error("Can't find resource in jar: ${resource.path}")
            ImageIO.read(res)
        } else {
            ImageIO.read(File(resource.path))
        }
    }

    fun readImageStream(resource: TilesetResource): InputStream {
        return if (isJarResource(resource)) {
            this::class.java.getResource(resource.path).openStream()
        } else {
            File(resource.path).inputStream()
        }
    }

    private fun isJarResource(resource: TilesetResource) =
        resource.tilesetSourceType == TilesetSourceType.JAR
}
