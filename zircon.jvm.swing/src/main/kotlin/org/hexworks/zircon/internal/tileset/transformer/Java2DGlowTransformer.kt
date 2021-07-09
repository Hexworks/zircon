package org.hexworks.zircon.internal.tileset.transformer

import com.jhlabs.image.GaussianFilter
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.modifier.Glow
import org.hexworks.zircon.api.tileset.TextureTransformer
import org.hexworks.zircon.api.tileset.TileTexture
import org.hexworks.zircon.internal.tileset.impl.DefaultTileTexture
import java.awt.Color
import java.awt.Graphics2D
import java.awt.image.BufferedImage

class Java2DGlowTransformer : TextureTransformer<BufferedImage> {

    val cloner = Java2DTextureCloner()

    override fun transform(texture: TileTexture<BufferedImage>, tile: Tile): TileTexture<BufferedImage> {
        texture.also { t ->
            t.texture.let { txt ->
                txt.graphics.apply {

                    if (tile.foregroundColor == tile.backgroundColor) {
                        return t
                    }

                    // Get character image:
                    val charImage = swapColor(
                        t,
                        tile.backgroundColor.toAWTColor(),
                        Color(0, 0, 0, 0),
                        tile
                    )

                    // Generate glow image:
                    val filter = GaussianFilter()
                    filter.radius = tile.modifiers.filterIsInstance<Glow>().first().radius
                    val glowImage = filter.filter(charImage.texture, null)

                    // Combine images and background:
                    val result = BufferedImage(txt.width, txt.height, BufferedImage.TYPE_INT_ARGB)
                    val gc = result.graphics as Graphics2D

                    gc.color = tile.backgroundColor.toAWTColor()
                    gc.fillRect(0, 0, result.width, result.height)
                    gc.drawImage(glowImage, 0, 0, null)
                    gc.drawImage(charImage.texture, 0, 0, null)
                    gc.dispose()

                    return DefaultTileTexture(
                        width = txt.width,
                        height = txt.height,
                        texture = result
                    )
                }
            }
        }
    }

    private fun swapColor(texture: TileTexture<BufferedImage>, oldColor: Color, newColor: Color, tile: Tile)
            : TileTexture<BufferedImage> {
        val result = cloner.transform(texture, tile)
        val image = result.texture
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

        return DefaultTileTexture(
            width = texture.width,
            height = texture.height,
            texture = image
        )
    }
}
