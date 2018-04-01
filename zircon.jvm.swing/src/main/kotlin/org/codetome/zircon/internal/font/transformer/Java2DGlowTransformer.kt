package org.codetome.zircon.internal.font.transformer

import com.jhlabs.image.GaussianFilter
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.extension.toAWTColor
import org.codetome.zircon.api.font.FontTextureRegion
import org.codetome.zircon.internal.font.FontRegionTransformer
import org.codetome.zircon.internal.font.impl.Java2DFontTextureRegion
import java.awt.Color
import java.awt.Graphics2D
import java.awt.image.BufferedImage

class Java2DGlowTransformer : FontRegionTransformer<BufferedImage> {

    val cloner = Java2DFontRegionCloner()

    override fun transform(region: FontTextureRegion<BufferedImage>, textCharacter: TextCharacter): FontTextureRegion<BufferedImage> {
        return region.also {
            it.getBackend().let { backend ->
                backend.graphics.apply {

                    if (textCharacter.getForegroundColor() == textCharacter.getBackgroundColor()) {
                        return region
                    }

                    // Get character image:
                    val charImage = swapColor(region,
                            textCharacter.getBackgroundColor().toAWTColor(),
                            Color(0, 0, 0, 0),
                            textCharacter)

                    // Generate glow image:
                    val filter = GaussianFilter()
                    filter.radius = 5f
                    val glowImage = filter.filter(charImage.getBackend(), null)

                    // Combine images and background:
                    val image = region.getBackend()
                    val result = BufferedImage(image.width, image.height, BufferedImage.TYPE_INT_ARGB)
                    val gc = result.graphics as Graphics2D

                    gc.color = textCharacter.getBackgroundColor().toAWTColor()
                    gc.fillRect(0, 0, result.width, result.height)
                    gc.drawImage(glowImage, 0, 0, null)
                    gc.drawImage(charImage.getBackend(), 0, 0, null)
                    gc.dispose()

                    return Java2DFontTextureRegion(result)
                }
            }
        }
    }

    private fun swapColor(region: FontTextureRegion<BufferedImage>, oldColor: Color, newColor: Color, textCharacter: TextCharacter)
            : FontTextureRegion<BufferedImage> {
        val result = cloner.transform(region, textCharacter)
        val image = result.getBackend()
        val newRGB = newColor.rgb
        val oldRGB = oldColor.rgb

        for (y in 0 until image.height) {
            for (x in 0 until image.width) {
                val pixel = image.getRGB(x, y)

                if (pixel == oldRGB) {
                    image.setRGB(x, y, newRGB)
                }
            }
        }

        return Java2DFontTextureRegion(image)
    }
}
