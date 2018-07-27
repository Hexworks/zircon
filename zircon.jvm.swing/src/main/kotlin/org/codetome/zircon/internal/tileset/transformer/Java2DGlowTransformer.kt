package org.codetome.zircon.internal.tileset.transformer

import com.jhlabs.image.GaussianFilter
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.tileset.TileTexture
import org.codetome.zircon.api.interop.toAWTColor
import org.codetome.zircon.internal.tileset.TileTextureTransformer
import org.codetome.zircon.internal.tileset.impl.Java2DTileTexture
import java.awt.Color
import java.awt.Graphics2D
import java.awt.image.BufferedImage

class Java2DGlowTransformer : TileTextureTransformer<BufferedImage> {

    val cloner = Java2DTileTextureCloner()

    override fun transform(texture: TileTexture<BufferedImage>, tile: Tile): TileTexture<BufferedImage> {
        return texture.also {
            it.getBackend().let { backend ->
                backend.graphics.apply {

                    if (tile.getForegroundColor() == tile.getBackgroundColor()) {
                        return texture
                    }

                    // Get character image:
                    val charImage = swapColor(texture,
                            tile.getBackgroundColor().toAWTColor(),
                            Color(0, 0, 0, 0),
                            tile)

                    // Generate glow image:
                    val filter = GaussianFilter()
                    filter.radius = 5f
                    val glowImage = filter.filter(charImage.getBackend(), null)

                    // Combine images and background:
                    val image = texture.getBackend()
                    val result = BufferedImage(image.width, image.height, BufferedImage.TYPE_INT_ARGB)
                    val gc = result.graphics as Graphics2D

                    gc.color = tile.getBackgroundColor().toAWTColor()
                    gc.fillRect(0, 0, result.width, result.height)
                    gc.drawImage(glowImage, 0, 0, null)
                    gc.drawImage(charImage.getBackend(), 0, 0, null)
                    gc.dispose()

                    return Java2DTileTexture(tile.generateCacheKey(), result)
                }
            }
        }
    }

    private fun swapColor(region: TileTexture<BufferedImage>, oldColor: Color, newColor: Color, tile: Tile)
            : TileTexture<BufferedImage> {
        val result = cloner.transform(region, tile)
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

        return Java2DTileTexture(tile.generateCacheKey(), image)
    }
}
