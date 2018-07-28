package org.codetome.zircon.poc.drawableupgrade.texture

import org.codetome.zircon.poc.drawableupgrade.tile.Tile
import java.awt.image.BufferedImage

class BufferedImageTileTextureCloner : TileTextureTransformer<BufferedImage> {

    override fun transform(texture: TileTexture<BufferedImage>, tile: Tile<out Any>): TileTexture<BufferedImage> {
        val txt = texture.getTexture()
        return DefaultTileTexture(
                width = txt.width,
                height = txt.height,
                texture = BufferedImage(txt.width, txt.height, BufferedImage.TRANSLUCENT).let { clone ->
                    clone.graphics.apply {
                        drawImage(txt, 0, 0, null)
                        dispose()
                    }
                    clone
                })
    }
}
