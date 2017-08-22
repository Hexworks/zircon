package org.codetome.zircon.internal.font.transformer

import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.internal.font.FontRegionTransformer
import java.awt.image.BufferedImage

class Java2DUnderlineTransformer : FontRegionTransformer<BufferedImage> {

    override fun transform(region: BufferedImage, textCharacter: TextCharacter): BufferedImage {
        return region.also {
            it.graphics.apply {
                color = textCharacter.getForegroundColor().toAWTColor()
                fillRect(0, region.height - 2, region.width, 2)
                dispose()
            }
        }
    }
}