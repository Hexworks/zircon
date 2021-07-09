package org.hexworks.zircon.internal.tileset.transformer

import org.hexworks.zircon.api.tileset.TileTexture
import org.hexworks.zircon.api.tileset.TextureTransformer
import org.hexworks.zircon.internal.tileset.impl.DefaultTileTexture
import org.hexworks.zircon.api.data.Tile
import java.awt.image.BufferedImage

class Java2DTextureCloner : TextureTransformer<BufferedImage> {

    override fun transform(texture: TileTexture<BufferedImage>, tile: Tile): TileTexture<BufferedImage> {
        val txt = texture.texture
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
