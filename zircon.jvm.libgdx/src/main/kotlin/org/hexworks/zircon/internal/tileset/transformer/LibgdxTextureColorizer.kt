package org.hexworks.zircon.internal.tileset.transformer

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.g2d.TextureRegion
import com.badlogic.gdx.graphics.glutils.PixmapTextureData
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.tileset.TextureTransformer
import org.hexworks.zircon.api.tileset.TileTexture

class LibgdxTextureColorizer : TextureTransformer<TextureRegion> {
    override fun transform(texture: TileTexture<TextureRegion>, tile: Tile): TileTexture<TextureRegion> {
        if(texture.texture.texture.textureData.isPrepared.not()) {
            texture.texture.texture.textureData.prepare()
        }
        val pixmap = texture.texture.texture.textureData.consumePixmap()
        for (x in 0 until pixmap.width) {
            for (y in 0 until pixmap.height) {
                val ax = getChannelFromRGBA8888(pixmap.getPixel(x, y), "alpha")
                if(ax < 50) {
                    pixmap.drawPixel(x, y, colorToRGBA8888(
                            Color(
                                    tile.backgroundColor.red / 255f,
                                    tile.backgroundColor.green / 255f,
                                    tile.backgroundColor.blue / 255f,
                                    tile.backgroundColor.alpha / 255f
                            )
                    ))
                } else {
                    pixmap.drawPixel(x, y, pixmap.getPixel(x, y))
                }
            }
        }
        texture.texture.texture.load(PixmapTextureData(pixmap, pixmap.format, false, false))
        texture.texture.texture.textureData.disposePixmap()
        return texture
    }

    private fun colorToRGBA8888(color: Color) : Int {
        val red = (color.r * 255).toInt()
        val green = (color.g * 255).toInt()
        val blue = (color.b * 255).toInt()
        val alpha = (color.a * 255).toInt()

        return (red shl 24) or (green shl 16) or (blue shl 8) or (alpha shl 0)
    }

    private fun getChannelFromRGBA8888(rgba8888: Int, channel: String) : Int {
        var colStr = Integer.toHexString(rgba8888)
        if(colStr.length < 8) {
            colStr = colStr.padStart(8, '0')
        }
        when(channel) {
            "red" -> colStr = colStr.substring(0, 2)
            "green" -> colStr = colStr.substring(2, 4)
            "blue" -> colStr = colStr.substring(4, 6)
            "alpha" -> colStr = colStr.substring(6)
        }
        return Integer.parseInt(colStr, 16)
    }
}