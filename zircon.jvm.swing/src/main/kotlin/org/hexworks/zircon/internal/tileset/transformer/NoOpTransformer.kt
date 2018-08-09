package org.hexworks.zircon.internal.tileset.transformer

import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.tileset.TileTexture
import org.hexworks.zircon.api.tileset.TileTextureTransformer
import java.awt.image.BufferedImage

class NoOpTransformer : TileTextureTransformer<BufferedImage> {
    override fun transform(texture: TileTexture<BufferedImage>, tile: Tile) = texture
}
