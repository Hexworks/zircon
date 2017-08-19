package org.codetome.zircon.font.impl.transformer

import org.codetome.zircon.TextCharacter
import org.codetome.zircon.font.FontRegionTransformer
import java.awt.geom.AffineTransform
import java.awt.image.AffineTransformOp
import java.awt.image.BufferedImage

class Java2DHorizontalFlipper : FontRegionTransformer<BufferedImage> {

    override fun transform(region: BufferedImage, textCharacter: TextCharacter): BufferedImage {
        val tx = AffineTransform.getScaleInstance(-1.0, 1.0)
        tx.translate(-region.width.toDouble(), 0.0)
        return AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR).filter(region, null)
    }
}