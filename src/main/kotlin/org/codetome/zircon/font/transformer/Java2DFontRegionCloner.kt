package org.codetome.zircon.font.transformer

import org.codetome.zircon.TextCharacter
import org.codetome.zircon.font.FontRegionTransformer
import java.awt.image.BufferedImage

class Java2DFontRegionCloner : FontRegionTransformer<BufferedImage> {

    override fun transform(region: BufferedImage, textCharacter: TextCharacter): BufferedImage {
        val newImage = BufferedImage(region.width, region.height, BufferedImage.TRANSLUCENT)
        val g = newImage.graphics
        g.color = textCharacter.getBackgroundColor().toAWTColor()
        g.fillRect(0, 0, region.width, region.height)
        g.drawImage(region, 0, 0, null)
        g.dispose()
        return newImage
    }
}