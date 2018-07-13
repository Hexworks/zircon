package org.codetome.zircon.internal.font.transformer

import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.font.FontTextureRegion
import org.codetome.zircon.internal.font.FontRegionTransformer
import org.codetome.zircon.internal.font.impl.Java2DFontTextureRegion
import java.awt.geom.AffineTransform
import java.awt.image.AffineTransformOp
import java.awt.image.BufferedImage

class Java2DVerticalFlipper : FontRegionTransformer<BufferedImage> {

    override fun transform(region: FontTextureRegion<BufferedImage>, textCharacter: TextCharacter): FontTextureRegion<BufferedImage> {
        val backend = region.getBackend()
        val tx = AffineTransform.getScaleInstance(1.0, -1.0)
        tx.translate(0.0, -backend.height.toDouble())
        return  Java2DFontTextureRegion(
                cacheKey = textCharacter.generateCacheKey(),
                backend = AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR).filter(backend, null))
    }
}
