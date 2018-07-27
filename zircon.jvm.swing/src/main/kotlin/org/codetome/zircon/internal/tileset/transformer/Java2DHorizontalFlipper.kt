package org.codetome.zircon.internal.tileset.transformer

import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.tileset.TileTexture
import org.codetome.zircon.internal.tileset.TileTextureTransformer
import org.codetome.zircon.internal.tileset.impl.Java2DTileTexture
import java.awt.geom.AffineTransform
import java.awt.image.AffineTransformOp
import java.awt.image.BufferedImage

class Java2DHorizontalFlipper : TileTextureTransformer<BufferedImage> {

    override fun transform(texture: TileTexture<BufferedImage>, tile: Tile): TileTexture<BufferedImage> {
        val backend = texture.getBackend()
        val tx = AffineTransform.getScaleInstance(-1.0, 1.0)
        tx.translate(-backend.width.toDouble(), 0.0)
        return Java2DTileTexture(
                cacheKey = tile.generateCacheKey(),
                backend = AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR).filter(backend, null))
    }
}
