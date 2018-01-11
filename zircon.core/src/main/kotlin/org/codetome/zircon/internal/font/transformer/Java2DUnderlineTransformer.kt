package org.codetome.zircon.internal.font.transformer

import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.font.FontTextureRegion
import org.codetome.zircon.internal.font.FontRegionTransformer
import java.awt.image.BufferedImage

class Java2DUnderlineTransformer : FontRegionTransformer<BufferedImage> {

    override fun transform(region: FontTextureRegion<BufferedImage>, textCharacter: TextCharacter): FontTextureRegion<BufferedImage> {
        return region.also {
            it.getBackend().let { backend ->
                backend.graphics.apply {
                    color = textCharacter.getForegroundColor().toAWTColor()
                    fillRect(0, backend.height - 2, backend.width, 2)
                    dispose()
                }
            }
        }
    }
}