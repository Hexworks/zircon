package org.codetome.zircon.internal.font.transformer

import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.font.FontTextureRegion
import org.codetome.zircon.internal.font.FontRegionTransformer
import org.codetome.zircon.internal.font.impl.Java2DFontTextureRegion
import java.awt.image.BufferedImage

class Java2DFontRegionCloner : FontRegionTransformer<BufferedImage> {

    override fun transform(region: FontTextureRegion<BufferedImage>, textCharacter: TextCharacter): FontTextureRegion<BufferedImage> {
        val backend = region.getBackend()
        return Java2DFontTextureRegion(
                cacheKey = textCharacter.generateCacheKey(),
                backend = BufferedImage(backend.width, backend.height, BufferedImage.TRANSLUCENT).let { clone ->
                    clone.graphics.apply {
                        drawImage(backend, 0, 0, null)
                        dispose()
                    }
                    clone
                })
    }
}
