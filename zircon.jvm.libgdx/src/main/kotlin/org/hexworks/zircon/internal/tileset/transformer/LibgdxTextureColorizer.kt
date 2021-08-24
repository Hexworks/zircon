package org.hexworks.zircon.internal.tileset.transformer

import com.badlogic.gdx.graphics.g2d.TextureRegion
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.tileset.TextureTransformer
import org.hexworks.zircon.api.tileset.TileTexture

class LibgdxTextureColorizer : TextureTransformer<TextureRegion> {

    override val targetType = TextureRegion::class

    override fun transform(texture: TileTexture<TextureRegion>, tile: Tile): TileTexture<TextureRegion> {
        return texture
    }

    private fun getChannelFromRGBA8888(rgba8888: Int, channel: String): Int {
        var colStr = Integer.toHexString(rgba8888)
        if (colStr.length < 8) {
            colStr = colStr.padStart(8, '0')
        }
        when (channel) {
            "red" -> colStr = colStr.substring(0, 2)
            "green" -> colStr = colStr.substring(2, 4)
            "blue" -> colStr = colStr.substring(4, 6)
            "alpha" -> colStr = colStr.substring(6)
        }
        return Integer.parseInt(colStr, 16)
    }
}
