package org.codetome.zircon.font.transformer

import org.codetome.zircon.TextCharacter
import org.codetome.zircon.font.FontRegionTransformer
import java.awt.Color
import java.awt.Graphics
import java.awt.image.BufferedImage

class Java2DFontRegionColorizer: FontRegionTransformer<BufferedImage> {

    override fun transform(region: BufferedImage, textCharacter: TextCharacter): BufferedImage {
        val r = textCharacter.getForegroundColor().getRed().toFloat() / 255
        val g = textCharacter.getForegroundColor().getGreen().toFloat() / 255
        val b = textCharacter.getForegroundColor().getBlue().toFloat() / 255

        (0..region.width - 1).forEach { x ->
            (0..region.height - 1).forEach { y ->
                val currentColor = Color(region.getRGB(x, y))
                val newColor = if(currentColor.alpha == 0) {
                    textCharacter.getBackgroundColor().toAWTColor()
                } else {
                    Color(
                            (currentColor.red * r).toInt(),
                            (currentColor.green * g).toInt(),
                            (currentColor.blue * b).toInt())
                }
                region.setRGB(x, y, newColor.rgb)
            }
        }
        return region
    }
}