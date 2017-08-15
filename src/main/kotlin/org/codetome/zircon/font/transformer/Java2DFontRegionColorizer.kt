package org.codetome.zircon.font.transformer

import org.codetome.zircon.TextCharacter
import org.codetome.zircon.TextColor
import org.codetome.zircon.font.FontRegionTransformer
import java.awt.Color
import java.awt.Graphics
import java.awt.image.BufferedImage

class Java2DFontRegionColorizer : FontRegionTransformer<BufferedImage> {

    override fun transform(region: BufferedImage, textCharacter: TextCharacter): BufferedImage {
        val r = textCharacter.getForegroundColor().getRed().toFloat() / 255
        val g = textCharacter.getForegroundColor().getGreen().toFloat() / 255
        val b = textCharacter.getForegroundColor().getBlue().toFloat() / 255

        (0..region.width - 1).forEach { i ->
            (0..region.height - 1).forEach { j ->
                val ax = region.colorModel.getAlpha(region.raster.getDataElements(i, j, null))
                var rx = region.colorModel.getRed(region.raster.getDataElements(i, j, null))
                var gx = region.colorModel.getGreen(region.raster.getDataElements(i, j, null))
                var bx = region.colorModel.getBlue(region.raster.getDataElements(i, j, null))
                rx = (rx * r).toInt()
                gx = (gx * g).toInt()
                bx = (bx * b).toInt()
                region.setRGB(i, j, (ax shl 24) or (rx shl 16) or (gx shl 8) or (bx shl 0))
            }
        }
        return region
    }

}