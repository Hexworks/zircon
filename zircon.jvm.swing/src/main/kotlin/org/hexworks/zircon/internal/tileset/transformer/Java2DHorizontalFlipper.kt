package org.hexworks.zircon.internal.tileset.transformer

import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.tileset.TileTexture
import org.hexworks.zircon.api.tileset.transformer.Java2DTextureTransformer
import org.hexworks.zircon.internal.tileset.impl.DefaultTileTexture
import java.awt.geom.AffineTransform
import java.awt.image.AffineTransformOp
import java.awt.image.BufferedImage

class Java2DHorizontalFlipper : Java2DTextureTransformer() {

    override fun transform(texture: TileTexture<BufferedImage>, tile: Tile): TileTexture<BufferedImage> {
        val backend = texture.texture
        val tx = AffineTransform.getScaleInstance(-1.0, 1.0)
        tx.translate(-backend.width.toDouble(), 0.0)
        return DefaultTileTexture(
            width = texture.width,
            height = texture.height,
            texture = AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR).filter(backend, null),
            cacheKey = tile.cacheKey
        )

    }
}
