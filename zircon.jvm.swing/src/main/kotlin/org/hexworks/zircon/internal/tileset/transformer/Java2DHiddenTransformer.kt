package org.hexworks.zircon.internal.tileset.transformer

import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.tileset.TileTexture
import org.hexworks.zircon.api.tileset.TextureTransformer
import java.awt.image.BufferedImage

class Java2DHiddenTransformer : TextureTransformer<BufferedImage> {

    override fun transform(texture: TileTexture<BufferedImage>, tile: Tile): TileTexture<BufferedImage> {
        return texture.also {
            it.texture().let { txt ->
                txt.graphics.apply {
                    color = tile.getBackgroundColor().toAWTColor()
                    fillRect(0, 0, txt.width, txt.height)
                    dispose()
                }
            }
        }
    }
}
