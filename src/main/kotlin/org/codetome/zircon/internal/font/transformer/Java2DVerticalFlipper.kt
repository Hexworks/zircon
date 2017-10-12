package org.codetome.zircon.internal.font.transformer

import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.font.FontTextureRegion
import org.codetome.zircon.internal.font.FontRegionTransformer
import org.codetome.zircon.internal.font.impl.Java2DFontTextureRegion
import java.awt.geom.AffineTransform
import java.awt.image.AffineTransformOp
import java.awt.image.BufferedImage

class Java2DVerticalFlipper : FontRegionTransformer {

    override fun transform(region: FontTextureRegion, textCharacter: TextCharacter): FontTextureRegion {
        val backend = region.getJava2DBackend()
        val tx = AffineTransform.getScaleInstance(1.0, -1.0)
        tx.translate(0.0, -backend.height.toDouble())
        return  Java2DFontTextureRegion(AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR).filter(backend, null))
    }
}