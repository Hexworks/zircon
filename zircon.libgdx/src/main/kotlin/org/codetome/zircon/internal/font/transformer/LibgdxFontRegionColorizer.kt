package org.codetome.zircon.internal.font.transformer

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.font.FontTextureRegion
import org.codetome.zircon.internal.font.FontRegionTransformer


class LibgdxFontRegionColorizer : FontRegionTransformer<TextureRegion> {

    override fun transform(region: FontTextureRegion<TextureRegion>, textCharacter: TextCharacter): FontTextureRegion<TextureRegion> {
        val r = textCharacter.getForegroundColor().getRed().toFloat() / 255
        val g = textCharacter.getForegroundColor().getGreen().toFloat() / 255
        val b = textCharacter.getForegroundColor().getBlue().toFloat() / 255

        val backend = region.getBackend()
        val texture = backend.texture
        if (!texture.textureData.isPrepared) {
            texture.textureData.prepare()
        }
        val pixmap = texture.textureData.consumePixmap()
        (0 until backend.regionWidth).forEach { x ->
            (0 until backend.regionHeight).forEach { y ->


                val color = Color(pixmap.getPixel(backend.regionX + x, backend.regionY + y))
                // you could now draw that color at (x, y) of another pixmap of the size (regionWidth, regionHeight)

                val ax = color.a
                var rx = color.r
                var gx = color.g
                var bx = color.b
                rx *= r
                gx *= g
                bx *= b
                if (ax < 50) {
                    pixmap.drawPixel(x, y, textCharacter.getBackgroundColor().toAWTColor().rgb)
                } else {
                    pixmap.drawPixel(x, y, Color(rx, gx, bx, ax).toIntBits())
                }
            }
        }
        return region
    }

}
