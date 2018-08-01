package org.codetome.zircon.gui.swing.impl

import org.codetome.zircon.api.tileset.TileTexture
import org.codetome.zircon.api.tileset.TileTextureTransformer
import org.codetome.zircon.internal.tileset.impl.DefaultTileTexture
import org.codetome.zircon.api.data.Tile
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
