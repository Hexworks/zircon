package org.hexworks.zircon.internal.tileset.transformer

import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.tileset.TileTexture
import org.hexworks.zircon.api.tileset.transformer.Java2DTextureTransformer
import java.awt.image.BufferedImage

class Java2DNoOpTransformer : Java2DTextureTransformer() {

    override fun transform(texture: TileTexture<BufferedImage>, tile: Tile) = texture
}
