package org.hexworks.zircon.internal.tileset.transformer

import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.tileset.TileTexture
import org.hexworks.zircon.api.tileset.TextureTransformer
import org.hexworks.zircon.internal.tileset.impl.DefaultTileTexture
import java.awt.geom.AffineTransform
import java.awt.image.AffineTransformOp
import java.awt.image.BufferedImage

class Java2DVerticalFlipper : TextureTransformer<BufferedImage> {

    override fun transform(texture: TileTexture<BufferedImage>, tile: Tile): TileTexture<BufferedImage> {
        val txt = texture.texture
        val tx = AffineTransform.getScaleInstance(1.0, -1.0)
        tx.translate(0.0, -txt.height.toDouble())
        return DefaultTileTexture(
            width = txt.width,
            height = txt.height,
            texture = AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR).filter(txt, null),
            cacheKey = tile.cacheKey
        )
    }
}
