package org.codetome.zircon.internal.font.transformer

import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.internal.font.FontRegionTransformer
import java.awt.image.BufferedImage

class Java2DFontRegionCloner : FontRegionTransformer<BufferedImage> {

    override fun transform(region: BufferedImage, textCharacter: TextCharacter): BufferedImage {
        return BufferedImage(region.width, region.height, BufferedImage.TRANSLUCENT).let { clone ->
            clone.graphics.apply {
                drawImage(region, 0, 0, null)
                dispose()
            }
            clone
        }
    }
}