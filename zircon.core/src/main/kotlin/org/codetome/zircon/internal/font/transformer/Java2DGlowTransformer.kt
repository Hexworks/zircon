package org.codetome.zircon.internal.font.transformer

import com.jhlabs.image.GaussianFilter
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.font.FontTextureRegion
import org.codetome.zircon.internal.font.FontRegionTransformer
import org.codetome.zircon.internal.font.impl.Java2DFontTextureRegion
import java.awt.Color
import java.awt.Graphics2D
import java.awt.image.BufferedImage

class Java2DGlowTransformer : FontRegionTransformer {

    val cloner = Java2DFontRegionCloner()

    override fun transform(region: FontTextureRegion, textCharacter: TextCharacter): FontTextureRegion {
        return region.also {
            it.getJava2DBackend().let { backend ->
                backend.graphics.apply {

                    if(textCharacter.getForegroundColor() == textCharacter.getBackgroundColor()) {
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
                    val glowImage = filter.filter(charImage.getJava2DBackend(), null)

                    // Combine images and background:
                    val image = region.getJava2DBackend()
                    val result = BufferedImage(image.width, image.height, BufferedImage.TYPE_INT_ARGB)
                    val gc = result.graphics as Graphics2D

                    gc.color = textCharacter.getBackgroundColor().toAWTColor()
                    gc.fillRect(0, 0, result.width, result.height)
                    gc.drawImage(glowImage, 0, 0, null)
                    gc.drawImage(charImage.getJava2DBackend(), 0, 0, null)
                    gc.dispose()

                    return Java2DFontTextureRegion(result)
                }
            }
        }
    }

    private fun swapColor(region: FontTextureRegion, oldColor: Color, newColor: Color, textCharacter: TextCharacter): FontTextureRegion {
        val result = cloner.transform(region, textCharacter)
        val image = result.getJava2DBackend()
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