package org.codetome.zircon.internal.tileset.transformer

import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.tileset.TileTexture
import org.codetome.zircon.api.tileset.TileTextureTransformer
import org.codetome.zircon.internal.tileset.impl.DefaultTileTexture
import java.awt.geom.AffineTransform
import java.awt.image.AffineTransformOp
import java.awt.image.BufferedImage

class Java2DHorizontalFlipper : TileTextureTransformer<BufferedImage> {

    override fun transform(texture: TileTexture<BufferedImage>, tile: Tile): TileTexture<BufferedImage> {
        val backend = texture.getTexture()
        val tx = AffineTransform.getScaleInstance(-1.0, 1.0)
        tx.translate(-backend.width.toDouble(), 0.0)
        return DefaultTileTexture(
                width = texture.getWidth(),
                height = texture.getHeight(),
                texture = AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR).filter(backend, null))

    }
}
