package org.codetome.zircon.gui.swing.internal.tileset.transformer

import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.tileset.TileTexture
import org.codetome.zircon.api.tileset.TileTextureTransformer
import java.awt.image.BufferedImage

class Java2DUnderlineTransformer : TileTextureTransformer<BufferedImage> {

    override fun transform(texture: TileTexture<BufferedImage>, tile: Tile): TileTexture<BufferedImage> {
        return texture.also {
            it.getTexture().let { txt ->
                txt.graphics.apply {
                    color = tile.getForegroundColor().toAWTColor()
                    fillRect(0, txt.height - 2, txt.width, 2)
                    dispose()
                }
            }
        }
    }
}
