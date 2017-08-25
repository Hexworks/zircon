package org.codetome.zircon.internal.font.transformer

import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.internal.font.FontRegionTransformer
import java.awt.image.BufferedImage

class Java2DFontRegionColorizer : FontRegionTransformer<BufferedImage> {

    override fun transform(region: BufferedImage, textCharacter: TextCharacter): BufferedImage {
        val r = textCharacter.getForegroundColor().getRed().toFloat() / 255
        val g = textCharacter.getForegroundColor().getGreen().toFloat() / 255
        val b = textCharacter.getForegroundColor().getBlue().toFloat() / 255

        (0..region.width - 1).forEach { x ->
            (0..region.height - 1).forEach { y ->
                val ax = region.colorModel.getAlpha(region.raster.getDataElements(x, y, null))
                var rx = region.colorModel.getRed(region.raster.getDataElements(x, y, null))
                var gx = region.colorModel.getGreen(region.raster.getDataElements(x, y, null))
                var bx = region.colorModel.getBlue(region.raster.getDataElements(x, y, null))
                rx = (rx * r).toInt()
                gx = (gx * g).toInt()
                bx = (bx * b).toInt()
                if (ax < 50) {
                    region.setRGB(x, y, textCharacter.getBackgroundColor().toAWTColor().rgb)
                } else {
                    region.setRGB(x, y, (ax shl 24) or (rx shl 16) or (gx shl 8) or (bx shl 0))
                }
            }
        }
        return region
    }

}