package org.codetome.zircon.font.transformer

import org.codetome.zircon.TextCharacter
import org.codetome.zircon.font.FontRegionTransformer
import java.awt.image.BufferedImage

class Java2DFontRegionCloner : FontRegionTransformer<BufferedImage> {

    override fun transform(region: BufferedImage, textCharacter: TextCharacter): BufferedImage {
        return BufferedImage(region.width, region.height, BufferedImage.TRANSLUCENT).let { clone ->
            clone.graphics.apply {
                color = textCharacter.getBackgroundColor().toAWTColor()
                fillRect(0, 0, region.width, region.height)
                drawImage(region, 0, 0, null)
                dispose()
            }
            clone
        }
    }
}