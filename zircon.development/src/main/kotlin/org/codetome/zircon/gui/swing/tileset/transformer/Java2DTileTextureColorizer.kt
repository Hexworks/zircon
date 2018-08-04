package org.codetome.zircon.gui.swing.tileset.transformer

import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.tileset.TileTexture
import org.codetome.zircon.api.tileset.TileTextureTransformer
import java.awt.Color
import java.awt.image.BufferedImage

class Java2DTileTextureColorizer : TileTextureTransformer<BufferedImage> {

    override fun transform(texture: TileTexture<BufferedImage>, tile: Tile): TileTexture<BufferedImage> {
        val r = tile.getForegroundColor().getRed().toFloat() / 255
        val g = tile.getForegroundColor().getGreen().toFloat() / 255
        val b = tile.getForegroundColor().getBlue().toFloat() / 255

        val backend = texture.getTexture()
        (0 until backend.width).forEach { x ->
            (0 until backend.height).forEach { y ->
                val ax = backend.colorModel.getAlpha(backend.raster.getDataElements(x, y, null))
                var rx = backend.colorModel.getRed(backend.raster.getDataElements(x, y, null))
                var gx = backend.colorModel.getGreen(backend.raster.getDataElements(x, y, null))
                var bx = backend.colorModel.getBlue(backend.raster.getDataElements(x, y, null))
                rx = (rx * r).toInt()
                gx = (gx * g).toInt()
                bx = (bx * b).toInt()
                if (ax < 50) {
                    backend.setRGB(x, y, tile.getBackgroundColor().toAWTColor().rgb)
                } else {
                    backend.setRGB(x, y, (ax shl 24) or (rx shl 16) or (gx shl 8) or (bx shl 0))
                }
            }
        }
        return texture
    }

}

/**
 * Extension for easy conversion between [TextColor] and awt [Color].
 */
fun TextColor.toAWTColor(): java.awt.Color = Color(getRed(), getGreen(), getBlue(), getAlpha())

