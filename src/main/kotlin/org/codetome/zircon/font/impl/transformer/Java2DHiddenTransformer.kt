package org.codetome.zircon.font.impl.transformer

import org.codetome.zircon.TextCharacter
import org.codetome.zircon.font.FontRegionTransformer
import java.awt.image.BufferedImage

class Java2DHiddenTransformer : FontRegionTransformer<BufferedImage> {

    override fun transform(region: BufferedImage, textCharacter: TextCharacter): BufferedImage {
        return region.also {
            it.graphics.apply {
                color = textCharacter.getBackgroundColor().toAWTColor()
                fillRect(0, 0, region.width, region.height)
                dispose()
            }
        }
    }
}