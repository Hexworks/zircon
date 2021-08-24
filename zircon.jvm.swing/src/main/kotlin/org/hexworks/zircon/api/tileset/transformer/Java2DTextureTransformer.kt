package org.hexworks.zircon.api.tileset.transformer

import org.hexworks.zircon.api.tileset.TextureTransformer
import java.awt.image.BufferedImage

abstract class Java2DTextureTransformer : TextureTransformer<BufferedImage> {

    override val targetType = BufferedImage::class

}
